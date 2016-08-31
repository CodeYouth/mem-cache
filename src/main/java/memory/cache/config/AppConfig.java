package memory.cache.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * memory-cache source code.
 *
 * @Author codeyouth
 * @Version 0.1.0-SNAPSHOT
 * @Date 16/8/27
 */
@Configuration
public class AppConfig {
  
  @Bean
  @ConfigurationProperties("spring.datasource.druid")
  public DataSource dataSource() {
    return new DruidDataSource();
  }
  
}
