package hu.aestallon.vulpress.app.config;

import hu.aestallon.vulpress.app.mail.EmailService;
import hu.aestallon.vulpress.app.mail.EmailServiceImpl;
import hu.aestallon.vulpress.docu.DocumentComposer;
import hu.aestallon.vulpress.docu.DocumentComposerImpl;
import hu.aestallon.vulpress.docu.DocumentFormat;
import hu.aestallon.vulpress.docu.docx.WordDocumentImporter;
import hu.aestallon.vulpress.docu.importer.DocumentImporter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class VulpressAppConfig {


  @Bean
  @ConditionalOnBean(JavaMailSender.class)
  EmailService emailService(JavaMailSender javaMailSender) {
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

}
