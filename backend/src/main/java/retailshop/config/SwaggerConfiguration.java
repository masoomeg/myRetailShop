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

			return new Docket(DocumentationType.SWAGGER_2)
					.groupName("administration-api")
					.select()
					.apis(not(withClassAnnotation(ApiIgnore.class)))
					.paths(PathSelectors.ant("/api/**"))
					.build()
					.apiInfo(apiInfo())
				;
	}

	//http://localhost:7080/swagger-ui.html
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("MyRetailShop Administration API", "REST API for Product's crud oprations",
				"API", "Terms of service",
				"",
				"", "");
		return apiInfo;
	}

}
