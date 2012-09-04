package com.typeahead.repository.redis;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.annconia.api.ApiException;
import com.annconia.api.interceptor.RateLimitException;
import com.annconia.security.repository.SecurityUserRepository;
import com.typeahead.interceptor.CreditsRequiredException;

/**
 * Test case to show the usage of {@link SecurityUserRepository} and thus the Mongo
 * repository support in general.
 * 
 * @author Adam Scherer
 */

@SuppressWarnings("restriction")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/context/redis-context.xml" })
public class RedisIntegrationTest {

	private String key = "1234";

	@Resource(name = "stringRedisTemplate")
	private StringRedisTemplate redisTemplate;

	@Autowired
	RedisRepository repository;

	@Before
	public void setup() {
		redisTemplate.getConnectionFactory().getConnection().flushDb();

		repository.addCredits(key, 1);
	}

	@Test
	public void simpleRateLimit() {
		repository.validateRate(key, 10);
	}

	@Test
	public void underRateLimit() {
		for (int i = 0; i <= 100; i++) {
			repository.validateRate(key, 100);
		}
	}

	@Test(expected = RateLimitException.class)
	public void overRateLimit() {
		for (int i = 0; i <= 101; i++) {
			repository.validateRate(key, 100);
		}
	}

	@Test
	public void simpleCreditLimit() {
		repository.validateCredits(key, 100);
	}

	@Test
	public void underCreditLimit() {
		for (int i = 0; i <= 100; i++) {
			repository.validateCredits(key, 100);
		}
	}

	@Test(expected = ApiException.class)
	public void nullApiKey() {
		repository.validateCredits(null, 100);
	}

	@Test(expected = CreditsRequiredException.class)
	public void overCreditLimit() {
		for (int i = 0; i <= 101; i++) {
			repository.validateCredits(key, 100);
		}
	}

}
