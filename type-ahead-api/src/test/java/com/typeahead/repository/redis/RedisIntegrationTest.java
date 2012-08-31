package com.typeahead.repository.redis;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.annconia.api.interceptor.RateLimitException;
import com.annconia.security.repository.SecurityUserRepository;

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

	@Resource(name = "stringRedisTemplate")
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	RedisRepository repository;
	
	@Before
	public void setup() {
		redisTemplate.getConnectionFactory().getConnection().flushDb();
	}

	@Test
	public void simpleTest() {
		repository.validateRate("1234", 10);
	}

	@Test
	public void underLimitTest() {
		for (int i = 0; i <= 100; i++) {
			repository.validateRate("12345", 100);
		}
	}

	@Test(expected = RateLimitException.class)
	public void exceptionTest() {
		for (int i = 0; i <= 101; i++) {
			repository.validateRate("123456", 100);
		}
	}

}
