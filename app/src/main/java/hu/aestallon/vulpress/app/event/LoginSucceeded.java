package hu.aestallon.vulpress.app.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class LoginSucceeded extends ApplicationEvent {
  public LoginSucceeded(Object source, Clock clock) {
    super(source, clock);
  }
}
