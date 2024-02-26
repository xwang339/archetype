package com.lixin.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import com.lixin.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Configuration
// 标注启动了缓存
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {
	public final static Logger log = LoggerFactory.getLogger(CacheConfiguration.class);
	/**
	 * 远程缓存管理器配置，使用redis
	 * @param factory
	 * @return
	 */
	@Bean(name = "remoteCacheManager")
	public RedisCacheManager remoteCacheManager(RedisConnectionFactory factory) {
		//生成一个默认配置，通过config对象即可对缓存进行自定义配置
		RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig();
		//设置缓存的默认过期时间，也是使用Duration设置,商品数据高频访问,缓存时间太长导致内存不足,太短会增加数据库负担,需要根据实际情况更改配置
		defaultConfig = defaultConfig.entryTtl(Duration.ofMinutes(30)) 
				.disableCachingNullValues(); // 不缓存空值
		//对每个缓存空间应用不同的配置
		Map<String, RedisCacheConfiguration> configMap = new HashMap<String, RedisCacheConfiguration>();
		//远程redis缓存一定要remote-作为前缀,否则会调用本地缓存,要是ehcache没有配置,则会报错
		configMap.put("remote-item", defaultConfig);
		//使用自定义的缓存配置初始化一个cacheManager
        return RedisCacheManager.builder(factory)
				.initialCacheNames(configMap.keySet()) // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
				.withInitialCacheConfigurations(configMap).build();
	}


	/*
	 * 本地缓存管理器配置
	 * 据shared与否的设置,Spring分别通过CacheManager.create()或new
	 * CacheManager()方式来创建一个ehcache基地.
	 */
	@Bean(name = "localCacheManager")
	public SimpleCacheManager localCacheManager() {
	    SimpleCacheManager cacheManager = new SimpleCacheManager();
	    ArrayList<CaffeineCache> caches = new ArrayList<CaffeineCache>();
	    //构建新的缓存(1)
		caches.add(createCache("items",600,  TimeUnit.SECONDS,1000000));
	    cacheManager.setCaches(caches);
	    return cacheManager;
	}

	public static CaffeineCache createCache(String cacheName,Integer duration,TimeUnit timeUnit,Integer maximumSize){
		CaffeineCache cache = new CaffeineCache(cacheName,
				Caffeine.newBuilder().recordStats()
						.expireAfterWrite(duration, timeUnit)//600秒，10分钟
						.maximumSize(maximumSize)//最大数量
						.build());
		return cache;
	}


	@Bean
	@Primary // 多个实现，优先选择
	public CacheManager cacheManager(RedisCacheManager remoteCacheManager, SimpleCacheManager localCacheCacheManager) {
		MulCacheManager cacheManager = new MulCacheManager();

		cacheManager.setRemoteCacheManager(remoteCacheManager);
//        remoteCacheManager.getCacheConfigurations()
		cacheManager.setLocalCacheManager(localCacheCacheManager);
		return cacheManager;
	}

	@Bean
	@Primary // 多个实现，优先选择
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }

	
	/**
	 * spring缓存的key生成器，在没有指定key的情况下，就会使用这个健生成器
	 */
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuffer sb = new StringBuffer();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					if (null != obj) {
						sb.append(obj.toString());
					}
				}
				return sb.toString();
			}
		};
	}
//
//	@Bean(name = "redisson")
//	public RedissonClient redisson() {
//		Config config = new Config();
//		config.setTransportMode(TransportMode.NIO);
//	    //可以用"rediss://"来启用SSL连接
//		//2.8.0和之前的版本使用的格式
//		String redissonConnectionString = "redis://" + host + ":" + port;
//		//String redissonConnectionString = host + ":" + port;
//		log.debug("connect redis server:" + redissonConnectionString);
//		config.useSingleServer().setAddress(redissonConnectionString).setPassword(password).setConnectionMinimumIdleSize(minIdle).setConnectionPoolSize(maxActive / 2);
////		config.useSingleServer().setAddress(redissonConnectionString).setConnectionMinimumIdleSize(minIdle).setConnectionPoolSize(maxActive / 2);
//		//集群模式
//		//config.useClusterServers()
//	    //.setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
//	    //.addNodeAddress(redissonConnectionString).setPassword(password);
//		return Redisson.create(config);
//		//RedissonRxClient client = Redisson.createRx(config);//基于RxJava2标准的实现方式
//		//RedissonReactiveClient client = Redisson.createReactive(config);//基于Project Reactor标准的实现方式
//	}


	/**
	 * 多缓存设计
	 * 
	 * @author Croky.Zheng 2017年3月8日
	 */
	public static class MulCacheManager implements CacheManager {
		private CacheManager remoteCacheManager;
		private CacheManager localCacheManager;

		@Override
		public Cache getCache(String name) {
			if (name.startsWith("remote-")) {
				return remoteCacheManager.getCache(name);
			}
			return localCacheManager.getCache(name);
		}

		@Override
		public Collection<String> getCacheNames() {
			Collection<String> cacheNames = new HashSet<String>();
			if (null != localCacheManager) {
				cacheNames.addAll(localCacheManager.getCacheNames());
			}

			if (null != remoteCacheManager) {
				cacheNames.addAll(remoteCacheManager.getCacheNames());
			}
			return cacheNames;
		}

		public CacheManager getRemoteCacheManager() {
			return remoteCacheManager;
		}

		public void setRemoteCacheManager(CacheManager remoteCacheManager) {
			this.remoteCacheManager = remoteCacheManager;
		}

		public CacheManager getLocalCacheManager() {
			return localCacheManager;
		}

		public void setLocalCacheManager(CacheManager localCacheManager) {
			this.localCacheManager = localCacheManager;
		}
	}

	public static class ObjectRedisSerializer implements RedisSerializer<Object> {

		private Charset charset = Charset.forName("utf-8");

		private final String SPLIT_STRING = ":";

		private final String DEFAULT_CONTENT = "EMPTY";


		public ObjectRedisSerializer() {
			this(Charset.forName("UTF8"));
		}

		public ObjectRedisSerializer(Charset charset) {
			// Assert.notNull(charset);
			if (null == charset) {
				charset = Charset.defaultCharset();
			}
			this.charset = charset;
		}

		@Override
		public byte[] serialize(Object t) throws SerializationException {
			if (null == t) {
				t = DEFAULT_CONTENT;
			}
			return object2String(t).getBytes(charset);
		}

		@Override
		public Object deserialize(byte[] bytes) throws SerializationException {
			String content = null;
			// 理论上不会有null值
			if (null != bytes) {
				content = new String(bytes, charset);
				return parse(content);
			}
			return null;
		}

		private String object2String(Object object) {
			StringBuilder sber = new StringBuilder();
			sber.append(object.getClass().getName()).append(SPLIT_STRING).append(JsonUtils.toJsonString(object));
			return sber.toString();
		}

		private Object parse(String content) {
			int index = content.indexOf(SPLIT_STRING);
			if (index > 0) {
				String className = content.substring(0, index);
				Class<?> clazz;
				try {
					clazz = Class.forName(className);
					return JsonUtils.toObject(content.substring(index + 1, content.length()), clazz);
				} catch (ClassNotFoundException e) {
					log.error("create instance failed. class:" + content);
				}
			}
			return null;
		}

	}
}
