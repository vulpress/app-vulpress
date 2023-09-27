package hu.aestallon.vulpress.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static hu.aestallon.vulpress.app.test.util.Dates.TEST_INST;
import static hu.aestallon.vulpress.app.test.util.Dates.TEST_ZONE;

@TestConfiguration
@Import({H2DatabaseConfig.class, VulpressAppConfig.class})
public class ViewTestConfig {

  private static final Logger log = LoggerFactory.getLogger(ViewTestConfig.class);

  @Bean
  @Primary
  Clock testClock() {
    log.info("Initialised test clock at {}.", TEST_INST.atZone(TEST_ZONE));
    return Clock.fixed(TEST_INST, TEST_ZONE);
  }

}
