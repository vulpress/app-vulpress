package hu.aestallon.vulpress.app.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class LoginFailed extends ApplicationEvent {
  public LoginFailed(Object source, Clock clock) {
    super(source, clock);
  }
}
