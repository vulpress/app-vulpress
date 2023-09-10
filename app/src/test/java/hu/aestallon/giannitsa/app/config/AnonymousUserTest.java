package hu.aestallon.giannitsa.app.config;

import hu.aestallon.giannitsa.app.auth.UserRepository;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SpringBootTest(
    classes = {
        ViewTestConfig.class,
        UserRepository.class,
        AnonymousUserService.class
    },
    properties = {
        "giannitsa-db.settings.h2.username=test-sa",
        "giannitsa-db.settings.h2.password=test-sa",
        "giannitsa-db.settings.h2.dbase-url=jdbc:h2:file:./test;MODE=PostgreSQL;"
        + "DATABASE_TO_LOWER=TRUE;"
        + "CASE_INSENSITIVE_IDENTIFIERS=TRUE;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE"
    })
@ComponentScan(
    basePackages = {
        "hu.aestallon.giannitsa.app.domain",
        "hu.aestallon.giannitsa.app.view"
    })
@EnableJdbcRepositories(basePackages = "hu.aestallon.giannitsa.app")
@ActiveProfiles({"bootstrap", "h2"})
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AnonymousUserTest {
}
