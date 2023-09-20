package hu.aestallon.giannitsa.app.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Test
@DirtiesContext
public @interface DirtyTest {}
