package hu.aestallon.giannitsa.app.event;

import hu.aestallon.giannitsa.app.auth.User;
import org.springframework.context.ApplicationEvent;

public class AccountActivated extends ApplicationEvent {

  public AccountActivated(User user) {
    super(user);
  }

  public User user() {
    return (User) getSource();
  }

}
