package hu.aestallon.giannitsa.docu.importer;

import java.io.InputStream;
import java.nio.file.Path;

public interface DocumentImporter {

  DocumentImportResult doImport(InputStream inputStream, DocumentImportOptions options);

  default DocumentImportResult doImport(InputStream inputStream) {
    return doImport(inputStream, null);
  }

  DocumentImportResult doImport(Path path, DocumentImportOptions options);

}
