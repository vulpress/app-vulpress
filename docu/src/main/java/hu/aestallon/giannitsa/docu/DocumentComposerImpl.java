package hu.aestallon.giannitsa.docu;


import hu.aestallon.giannitsa.docu.exporter.DocumentExporter;
import hu.aestallon.giannitsa.docu.importer.DocumentImporter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DocumentComposerImpl implements DocumentComposer {

  private final Map<DocumentFormat, DocumentImporter> importersByFormat;
  private final Map<DocumentFormat, DocumentExporter> exportersByFormat;

  public DocumentComposerImpl() {
    this.importersByFormat = new HashMap<>();
    this.exportersByFormat = new HashMap<>();
  }

  @Override
  public DocumentImporter provideImporter(DocumentFormat format) {
    return Optional
        .ofNullable(importersByFormat.get(format))
        .orElseThrow();
  }

  @Override
  public DocumentExporter provideExporter(DocumentFormat format) {
    return null;
  }

  public DocumentComposerImpl importing(Customiser<DocumentImporterConfigurer> importer) {
    final var importConfig = new DocumentImporterConfigurer();
    importer.customize(importConfig);
    this.importersByFormat.putAll(importConfig.m);

    return this;
  }

  public DocumentComposerImpl exporting() {
    // TODO: Implement configurer and extract common parts
    return this;
  }

  @FunctionalInterface
  public interface Customiser<T extends DocumentOperationConfigurer> {
    void customize(T t);
  }


  public interface DocumentOperationConfigurer {

  }


  public static final class DocumentImporterConfigurer implements DocumentOperationConfigurer {

    private final Map<DocumentFormat, DocumentImporter> m;

    private DocumentImporterConfigurer() {
      m = new HashMap<>();
    }

    public DocumentImporterSelector format(DocumentFormat format) {
      return new DocumentImporterSelector(format);
    }

    public class DocumentImporterSelector {
      private final DocumentFormat format;

      private DocumentImporterSelector(DocumentFormat format) {
        this.format = format;
      }

      public DocumentImporterConfigurer with(DocumentImporter importer) {
        DocumentImporterConfigurer.this.m.put(format, importer);
        return DocumentImporterConfigurer.this;
      }
    }

  }

}
