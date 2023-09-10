package hu.aestallon.giannitsa.app.config;

import hu.aestallon.giannitsa.app.auth.User;
import hu.aestallon.giannitsa.app.auth.UserRepository;
import hu.aestallon.giannitsa.app.auth.UserService;

public class AdminUserService extends UserService {
  private static final User ADMIN_USER = new User("admin-user", "pw", ADMIN, false);

  public AdminUserService(UserRepository userRepository) {
    super(userRepository);
  }

  @Override
  public User currentUser() {
    return ADMIN_USER;
  }

}
