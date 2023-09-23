package hu.aestallon.vulpress.app.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({H2DatabaseConfig.class, VulpressAppConfig.class})
public class ViewTestConfig {
}
