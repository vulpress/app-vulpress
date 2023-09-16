package hu.aestallon.giannitsa.docu;

import hu.aestallon.giannitsa.docu.exporter.DocumentExporter;
import hu.aestallon.giannitsa.docu.importer.DocumentImporter;

public interface DocumentComposer {

  DocumentImporter provideImporter(DocumentFormat format);

  DocumentExporter provideExporter(DocumentFormat format);

}
