package hu.aestallon.giannitsa.docu.exporter;

import hu.aestallon.giannitsa.docu.model.Document;

public interface DocumentExporter {

  void export(Document document, DocumentExportOptions options);

}
