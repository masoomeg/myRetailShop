package retailshop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfiguration {

    /**
     * Support for Hibernate types in Jackson.
     */
    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

    /**
     * Jackson Afterburner module to speed up serialization/deserialization.
     */
    @Bean
    public AfterburnerModule afterburnerModule() {
        return new AfterburnerModule();
    }

    @Bean
    public ObjectMapper createObjectMapper() {
    	ObjectMapper mapper = new ObjectMapper();
        Hibernate5Module hibernateModule = new Hibernate5Module();
        hibernateModule.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
        mapper.registerModules(new JavaTimeModule(), new Jdk8Module(), new ParameterNamesModule(), hibernateModule);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return mapper;

    }

    @Bean
      public Jackson2ObjectMapperBuilderCustomizer addCustomBigDecimalDeserialization() {
          return new Jackson2ObjectMapperBuilderCustomizer() {

              @Override
              public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
              	/*
              	 * This is required because ZonedDateTime is used in domain model which is serialized as object by jackson and can
              	 * not be deserialized
              	 */
              	jacksonObjectMapperBuilder.modulesToInstall(new JavaTimeModule(), new Jdk8Module(), new ParameterNamesModule());
              	jacksonObjectMapperBuilder.modulesToInstall(new Hibernate5Module());

              	/*
              	 * this is required because swagger client is using joda time which does not support nanoseconds
              	 */
              	jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
              }
          };
      }
}
