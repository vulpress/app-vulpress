package hu.aestallon.vulpress.app.config;

import hu.aestallon.vulpress.app.mail.EmailService;
import hu.aestallon.vulpress.app.mail.EmailServiceImpl;
import hu.aestallon.vulpress.docu.DocumentComposer;
import hu.aestallon.vulpress.docu.DocumentComposerImpl;
import hu.aestallon.vulpress.docu.DocumentFormat;
import hu.aestallon.vulpress.docu.docx.WordDocumentImporter;
import hu.aestallon.vulpress.docu.importer.DocumentImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class VulpressAppConfig {

  private static final Logger log = LoggerFactory.getLogger(VulpressAppConfig.class);

  @Bean
  @ConditionalOnProperty(prefix = "spring.mail", name = "host")
  @Profile("mail")
  EmailService emailService(JavaMailSender javaMailSender) {
    log.info("VULPRESS - LAUNCHING WITH LIVE E-MAIL SERVICE");
    return new EmailServiceImpl(javaMailSender);
  }

  @Bean
  DocumentComposer documentComposer() {
    return new DocumentComposerImpl()
        .importing(i -> i
            .format(DocumentFormat.DOCX)
            .with(new WordDocumentImporter()));
  }

  @Bean
  DocumentImporter wordDocumentImporter() {
    return documentComposer().provideImporter(DocumentFormat.DOCX);
  }

  @Bean
  @ConditionalOnMissingBean
  Clock clock() {
    log.info("VULPRESS - Initialised central clock for UTC!");
    return Clock.systemUTC();
  }

}
