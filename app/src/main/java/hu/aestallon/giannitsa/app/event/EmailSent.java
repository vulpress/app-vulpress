package hu.aestallon.giannitsa.app.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class EmailSent extends ApplicationEvent {

  public EmailSent(Object source, Clock clock) {
    super(source, clock);
  }

}
