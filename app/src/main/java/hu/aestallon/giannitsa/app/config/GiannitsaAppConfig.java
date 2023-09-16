package hu.aestallon.giannitsa.app.config;

import hu.aestallon.giannitsa.docu.DocumentComposer;
import hu.aestallon.giannitsa.docu.DocumentComposerImpl;
import hu.aestallon.giannitsa.docu.DocumentFormat;
import hu.aestallon.giannitsa.docu.docx.WordDocumentImporter;
import hu.aestallon.giannitsa.docu.importer.DocumentImporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GiannitsaAppConfig {

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
