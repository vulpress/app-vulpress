package hu.aestallon.vulpress.app.event;

import hu.aestallon.vulpress.app.rest.model.AuthenticationRequest;
import org.springframework.context.ApplicationEvent;

public class RegistrationRejected extends ApplicationEvent {

  public static final String REASON_WEAK_PASSWORD    = "reg.rej.weak-password";
  public static final String REASON_USERNAME_INVALID = "reg.rej.username-invalid";
  public static final String REASON_USERNAME_TAKEN   = "reg.rej.username-taken";
  public static final String REASON_OTHER            = "reg.rej.other";

  private final String reason;

  public RegistrationRejected(AuthenticationRequest request, String reason) {
    super(request);
    this.reason = reason;
  }

  public AuthenticationRequest request() {
    return (AuthenticationRequest) getSource();
  }

  public String reason() {
    return reason;
  }

}
