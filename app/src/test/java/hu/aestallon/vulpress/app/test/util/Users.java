package hu.aestallon.vulpress.app.test.util;

import hu.aestallon.vulpress.app.auth.User;
import hu.aestallon.vulpress.app.auth.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public final class Users {

  public static final User ADMIN_USER = new User(1L, "admin-user", "admin@address", "pw",
      UserService.ADMIN, false, null, null);
  public static final User PLAIN_USER = new User(2L, "plain-user", "plain@domain", "pw",
      UserService.PLAIN,
      false, null, null);

  public static void asAdmin() {
    asUser(ADMIN_USER);
  }

  public static void asPlain() {
    asUser(PLAIN_USER);
  }

  private static void asUser(User u) {
    final SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(new UsernamePasswordAuthenticationToken(
        u, null, u.getAuthorities()));
    SecurityContextHolder.setContext(context);
  }

  public static void asAnonymous() {
    SecurityContextHolder.clearContext();
  }

  private Users() {}

}
