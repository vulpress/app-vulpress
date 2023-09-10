package hu.aestallon.giannitsa.app.config;

import hu.aestallon.giannitsa.app.config.H2DatabaseConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.annotation.Immutable;

@TestConfiguration
@Import({H2DatabaseConfig.class})
public class ViewTestConfig {
}
