package hu.aestallon.vulpress.docu.exporter;

import hu.aestallon.vulpress.docu.model.Document;

public interface DocumentExporter {

  void export(Document document, DocumentExportOptions options);

}
