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

import com.annconia.api.interceptor.RateLimitException;
import com.annconia.util.DateUtils;
import com.annconia.util.StringUtils;

@SuppressWarnings("restriction")
@Repository
public class RedisRepository {

	@Resource(name = "stringRedisTemplate")
	private StringRedisTemplate redisTemplate;

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