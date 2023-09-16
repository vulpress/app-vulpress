package hu.aestallon.giannitsa.docu.importer;


import hu.aestallon.giannitsa.docu.model.Document;

import java.util.Objects;

public sealed interface DocumentImportResult
    permits DocumentImportResult.Ok, DocumentImportResult.Err {

  // TODO: Remove when upgrading to Java 21!
  boolean isOk();

  record Ok(Document document) implements DocumentImportResult {

    public Ok {
      Objects.requireNonNull(document, "document cannot be null on OK result!");
    }

    @Override
    public boolean isOk() {
      return true;
    }
  }

  record Err(Throwable throwable, String errorCode) implements DocumentImportResult {
    public Err {
      Objects.requireNonNull(errorCode, "errorCode cannot be null!");
    }

    @Override
    public boolean isOk() {
      return false;
    }
  }

  static DocumentImportResult ok(Document document) {
    return new Ok(document);
  }

  static DocumentImportResult err(Throwable throwable) {
    return new Err(throwable, "import.exception");
  }

  static DocumentImportResult err(String errorCode) {
    return new Err(null, errorCode);
  }
}
