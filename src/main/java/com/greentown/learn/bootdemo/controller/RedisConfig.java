package com.greentown.learn.bootdemo.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.greentown.learn.bootdemo.common.FastJsonRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;


public class RedisConfig {
	private static Logger logger = Logger.getLogger(RedisConfig.class);
	
	@Value("${redis.pool.maxActive}")
	private int maxTotal;
	@Value("${redis.pool.maxIdle}")
	private int maxIdle;
	@Value("${redis.pool.minIdle}")
	private int minIdle;
	@Value("${redis.pool.maxWait}")
	private long maxWaitMillis;
	@Value("${redis.host}")
	private String hostName;
	@Value("${redis.port}")
	private int port;
	@Value("${redis.password}")
	private String password;
	@Value("${redis.timeout}")
	private int timeout;
	@Value("${redis.default.db}")
	private int db;
	

    @Bean  
    public JedisPoolConfig getRedisConfig(){  
        JedisPoolConfig config = new JedisPoolConfig();  
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;  
    }  

    

    @Bean  
    public JedisConnectionFactory getConnectionFactory(){  
        JedisConnectionFactory factory = new JedisConnectionFactory();  
        JedisPoolConfig config = getRedisConfig();  
        factory.setUsePool(true);
        factory.setHostName(hostName);
        factory.setPort(port);
        factory.setPassword(password);
        factory.setTimeout(timeout);
        factory.setDatabase(db);
        factory.setPoolConfig(config);  
        logger.info("JedisConnectionFactory bean init success.");  
        return factory;  
    }  
    
    
    @Bean  
    public RedisTemplate<?, ?> getRedisTemplate(){  
    	
        RedisTemplate<?,?> template = new RedisTemplate<>();
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        template.setEnableTransactionSupport(false);
        template.setEnableDefaultSerializer(false);
        template.setConnectionFactory(getConnectionFactory());
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(new FastJsonRedisSerializer<String>());
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(new FastJsonRedisSerializer<String>());
        return template;  
    }  
    
    
	
}
