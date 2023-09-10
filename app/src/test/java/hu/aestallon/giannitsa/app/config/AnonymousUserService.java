package hu.aestallon.giannitsa.app.config;

import hu.aestallon.giannitsa.app.auth.User;
import hu.aestallon.giannitsa.app.auth.UserRepository;
import hu.aestallon.giannitsa.app.auth.UserService;

final class AnonymousUserService extends UserService {

  public AnonymousUserService(UserRepository userRepository) {
    super(userRepository);
  }

  @Override
  public User currentUser() {
    return null;
  }
}
