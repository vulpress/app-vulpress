package hu.aestallon.giannitsa.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.aestallon.giannitsa.app.GiannitsaApp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
@Import({GiannitsaApp.class})
public class IntegrationTestConfig {

  @Bean
  @ConditionalOnMissingBean(ObjectMapper.class)
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  @ConditionalOnMissingBean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
