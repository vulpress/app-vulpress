package hu.aestallon.giannitsa.app.domain;

public class ForbiddenOperationException extends RuntimeException {

  public ForbiddenOperationException(String message) {
    super(message);
  }

}
