package hu.aestallon.giannitsa.app.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({H2DatabaseConfig.class, GiannitsaAppConfig.class})
public class ViewTestConfig {
}
