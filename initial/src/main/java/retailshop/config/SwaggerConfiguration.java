package retailshop.config;

import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.models.auth.In;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket swaggerSpringMvcPlugin() {


		/*
		 * Sample: https://springfox.github.io/springfox/docs/current/#configuring-security-schemes-and-contexts-an-overview
		 */
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("administration-api")
				.select()
				//Ignores controllers annotated with @CustomIgnore
				//                .apis(RequestHandlerSelectors.any()) //Selection by RequestHandler
				.apis(not(withClassAnnotation(ApiIgnore.class)))
                .paths(PathSelectors.ant("/api/**"))
				//.paths(PathSelectors.any()) // and by paths
				//                .paths(regex("/api/*"))
				.build()
				.apiInfo(apiInfo())
				//.securitySchemes(Arrays.asList(apiKey()))
				//.securityContexts(Arrays.asList(securityContext()))
				;

	}

	//http://localhost:7080/swagger-ui.html
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("AMX Administration API", "Administration API for AMX.", 
				"API TOS", "Terms of service", 
				"",
				"License of API", "API license URL");
		return apiInfo;
	}

}
