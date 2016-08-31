package memory.cache.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/31
 */
@EnableSwagger2
public class SwaggerConfig {
  @Value("${application.name}")
  private String serverName;
  
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName(serverName + "-API")
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(regex("^((?!/error).)*$"))
        .build()
        .apiInfo(apiInfo());
  }
  
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(serverName + " API")
        .termsOfServiceUrl("http://springfox.io")
        .license("Apache License Version 2.0")
        .version("2.0")
        .build();
  }
}
