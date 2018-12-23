package retailshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

/**
 * Created by Ma_Gorji on 2018/12/22.
 */


@org.springframework.context.annotation.Configuration
public class RedisConfig {

//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        return new JedisConnectionFactory();
//    }

//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory jedisConFactory
//          = new JedisConnectionFactory();
//        jedisConFactory.setHostName("localhost");
//        jedisConFactory.setPort(6379);
//        return jedisConFactory;
//    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setEnableTransactionSupport(true) ;
//        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
//        return template;
//    }



    @Bean
   	JedisConnectionFactory jedisConnectionFactory() {
   		return new JedisConnectionFactory();
   	}

   	@Bean
   	public RedisTemplate<String, Object> redisTemplate() {
   		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
   		template.setConnectionFactory(jedisConnectionFactory());
   		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
   		return template;
   	}

}
