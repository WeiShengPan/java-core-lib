package redis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * 原生jedis操作
 *
 * @author panws
 * @since 2017-08-29
 */
public class JedisClient {

	private static volatile JedisClient jedisClient;

	private static final String host = "";

	private static final String pwd = "";

	private Jedis jedis = null;

	public static JedisClient getInstance() {
		if (null == jedisClient) {
			synchronized (jedisClient) {
				if (null == jedisClient) {
					jedisClient = new JedisClient();
				}
			}
		}
		return jedisClient;
	}

	private JedisPoolConfig getPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(10);
		jedisPoolConfig.setMaxTotal(100);
		jedisPoolConfig.setMaxWaitMillis(3000);
		return jedisPoolConfig;
	}

	public String add(String key, String value) {

		try (JedisPool jedisPool = new JedisPool(getPoolConfig(), host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT,
				pwd)) {

			jedis = jedisPool.getResource();

			if (jedis.exists(key)) {
				System.out.println("key already exist.");
			}

			return jedis.set(key, value);

		}
	}

	public String get(String key) {

		try (JedisPool jedisPool = new JedisPool(getPoolConfig(), host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT,
				pwd)) {

			jedis = jedisPool.getResource();

			return jedis.get(key);

		}
	}

	public Boolean exists(String key) {

		try (JedisPool jedisPool = new JedisPool(getPoolConfig(), host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT,
				pwd)) {

			jedis = jedisPool.getResource();

			return jedis.exists(key);

		}
	}

	public Long delete(String key) {

		try (JedisPool jedisPool = new JedisPool(getPoolConfig(), host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT,
				pwd)) {

			jedis = jedisPool.getResource();

			return jedis.del(key);

		}
	}

	public static void main(String[] args) {

		JedisClient jedisClient = JedisClient.getInstance();

		System.out.println(jedisClient.add("a", "hahaha"));

		System.out.println(jedisClient.get("a"));
		System.out.println(jedisClient.get("b"));

		System.out.println(jedisClient.exists("a"));
		System.out.println(jedisClient.exists("b"));

		System.out.println(jedisClient.delete("a"));
		System.out.println(jedisClient.delete("b"));

		System.out.println(jedisClient.exists("a"));
		System.out.println(jedisClient.exists("b"));

	}

}
