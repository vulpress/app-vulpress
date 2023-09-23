package hu.aestallon.vulpress.docu;

import hu.aestallon.vulpress.docu.exporter.DocumentExporter;
import hu.aestallon.vulpress.docu.importer.DocumentImporter;

public interface DocumentComposer {

  DocumentImporter provideImporter(DocumentFormat format);

  DocumentExporter provideExporter(DocumentFormat format);

}
