package com.mmajd.gobuy.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.Session;

//@Configuration
@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private Integer redisPort;
    @Value("${spring.redis.password}")
    private String redisPassword;


    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort.intValue());
        return new JedisConnectionFactory(config);
    }

    @Primary
    @Bean
    public RedisTemplate<String, Session> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Session> template = new RedisTemplate<>();

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new CustomRedisSerializer());

        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }
}


/**
 * @see <a href="https://github.com/spring-projects/spring-data-redis/blob/main/src/main/java/org/springframework/data/redis/serializer/JdkSerializationRedisSerializer.java">Reference Implementation</a>
 */
class CustomRedisSerializer implements RedisSerializer<Object> {
    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer = new DeserializingConverter();

    static final byte[] EMPTY_ARRAY = new byte[0];

    public Object deserialize(byte[] bytes) {
        if (isEmpty(bytes)) {
            return null;
        }

        try {
            return deserializer.convert(bytes);
        } catch (Exception ex) {
            throw new SerializationException("Cannot deserialize", ex);
        }
    }

    public byte[] serialize(Object object) {
        if (object == null) {
            return EMPTY_ARRAY;
        }

        try {
            return serializer.convert(object);
        } catch (Exception ex) {
            /**
             * TODO: add logic here to only return EMPTY_ARRAY for known conditions
             * else throw the SerializationException
             * throw new SerializationException("Cannot serialize", ex);
             **/

            return EMPTY_ARRAY;
        }
    }

    private boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }
}