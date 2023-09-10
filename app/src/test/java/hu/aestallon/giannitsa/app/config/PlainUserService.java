package hu.aestallon.giannitsa.app.config;

import hu.aestallon.giannitsa.app.auth.User;
import hu.aestallon.giannitsa.app.auth.UserRepository;
import hu.aestallon.giannitsa.app.auth.UserService;

public class PlainUserService extends UserService {

  private static final User PLAIN_USER = new User("plain-user", "pw", PLAIN, false);

  public PlainUserService(UserRepository userRepository) {
    super(userRepository);
  }

  @Override
  public User currentUser() {
    return PLAIN_USER;
  }
}
