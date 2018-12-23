package retailshop.config;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * Created by Ma_Gorji on 2018/12/22.
 */
@Configuration
public class RedisAtomicLong {

    org.springframework.data.redis.support.atomic.RedisAtomicLong uniqueNbr;
    @Bean
        public org.springframework.data.redis.support.atomic.RedisAtomicLong getRedisAtomicLong(){
            ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(

                           RedisConfig.class);
                   Long id=1L;
                   	        try {

                   	            RedisConnectionFactory connectionFactory = (RedisConnectionFactory) ctx.getBean("jedisConnectionFactory");

                   	             uniqueNbr =

                   	                    new org.springframework.data.redis.support.atomic.RedisAtomicLong("RedisUniqueNbr", connectionFactory, 0);


//                   	          id=    uniqueNbr.incrementAndGet();
                   	          System.out.println("Redis Atomic Long: " + uniqueNbr.incrementAndGet());



                   	        } finally {

                   	            ctx.close();

                   	        }
        return uniqueNbr;
        }



}
