package hu.aestallon.giannitsa.app.domain;

public class ConstraintViolationException extends RuntimeException {

  public ConstraintViolationException(String message) {
    super(message);
  }

}
