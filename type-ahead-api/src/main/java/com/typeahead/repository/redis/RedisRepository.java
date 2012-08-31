package com.typeahead.repository.redis;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.annconia.api.interceptor.RateLimitException;
import com.annconia.util.DateUtils;
import com.annconia.util.StringUtils;
import com.typeahead.interceptor.CreditsRequiredException;

@SuppressWarnings("restriction")
@Repository
public class RedisRepository {

	@Resource(name = "stringRedisTemplate")
	private StringRedisTemplate redisTemplate;

	public void addCredits(String apiKey, int total) {
		redisTemplate.opsForValue().increment(KeyUtils.credits(apiKey), total);
	}

	public void removeCredits(String apiKey, int total) {
		addCredits(apiKey, total * -1);
	}

	@SuppressWarnings("all")
	public void validateCredits(String apiKey, int hitsPerCredit) {

		Assert.notNull(apiKey);

		final String creditsKey = KeyUtils.credits(apiKey);
		long credits = StringUtils.parseLong(redisTemplate.opsForValue().get(creditsKey));
		if (credits < 1) {
			throw new CreditsRequiredException("Not enough credits to execute api.");
		}

		final String hitsKey = KeyUtils.hits(apiKey);
		long hits = StringUtils.parseLong(redisTemplate.opsForValue().get(hitsKey));
		if (((double) hits / hitsPerCredit) > credits) {
			throw new CreditsRequiredException("Not enough credits to execute api.");
		}

		final String hitsHourlyKey = KeyUtils.hitsHourly(apiKey);

		redisTemplate.execute(new SessionCallback<Object>() {

			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {

				operations.opsForValue().increment(hitsKey, 1);
				operations.opsForValue().increment(hitsHourlyKey, 1);

				return null;
			}
		});

	}

	@SuppressWarnings("all")
	public void validateRate(String key, int limit) {

		long expire = DateUtils.truncate(new Date(), Calendar.MINUTE).getTime();
		final String rateKey = KeyUtils.rateLimit(key, expire);
		long count = StringUtils.parseLong(redisTemplate.opsForValue().get(rateKey));

		if (count > limit) {
			throw new RateLimitException("Rate limit was exceeded.");
		}

		redisTemplate.execute(new SessionCallback<Object>() {

			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {

				operations.opsForValue().increment(rateKey, 1);
				operations.expire(rateKey, 1, TimeUnit.MINUTES);

				return null;
			}
		});

	}
}