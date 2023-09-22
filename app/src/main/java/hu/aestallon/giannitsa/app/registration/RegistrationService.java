package hu.aestallon.giannitsa.app.registration;

import hu.aestallon.giannitsa.app.auth.User;
import hu.aestallon.giannitsa.app.auth.UserRepository;
import hu.aestallon.giannitsa.app.auth.UserService;
import hu.aestallon.giannitsa.app.event.AccountActivated;
import hu.aestallon.giannitsa.app.event.RegistrationAccepted;
import hu.aestallon.giannitsa.app.event.RegistrationRejected;
import hu.aestallon.giannitsa.app.rest.model.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RegistrationService {
  private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

  private static final String  SIMPLE_MAIL_REGEX = "^[^@]+@[^\\.@]+\\.[^\\.@]+$";
  private static final Pattern SIMPLE_MAIL_PTRN  = Pattern.compile(SIMPLE_MAIL_REGEX);

  private final ApplicationEventPublisher eventPublisher;
  private final UserRepository            userRepository;
  private final PasswordEncoder           passwordEncoder;


  public RegistrationResult register(AuthenticationRequest authenticationRequest) {
    log.debug("RegistrationService.register invoked for {}", authenticationRequest);

    final String username = authenticationRequest.getUsername().trim();
    final String rawPassword = authenticationRequest.getPassword().trim();
    if (!SIMPLE_MAIL_PTRN.matcher(username).matches()
        || rawPassword.isBlank()
        || rawPassword.length() < 8) {
      log.debug("{} does not meet criteria!", authenticationRequest);
      eventPublisher.publishEvent(new RegistrationRejected(
          authenticationRequest,
          RegistrationRejected.REASON_OTHER));
      return RegistrationResult.ERR_INSUFFICIENT;
    }

    if (userRepository.existsByUsername(username)) {
      log.debug("Cannot register {} because username is already taken!", authenticationRequest);
      eventPublisher.publishEvent(new RegistrationRejected(
          authenticationRequest,
          RegistrationRejected.REASON_USERNAME_TAKEN));
      return RegistrationResult.ERR_USERNAME_TAKEN;
    }

    User user = new User(username, passwordEncoder.encode(rawPassword), UserService.PLAIN, true)
        .token(Duration.ofHours(3));
    user = userRepository.save(user);
    log.debug("User created: {}", user);

    eventPublisher.publishEvent(new RegistrationAccepted(user));
    return RegistrationResult.OK;
  }

  public boolean verify(UUID registrationToken) {
    final var userOpt = userRepository.findOptionalByToken(registrationToken);
    if (userOpt.isEmpty()) {
      return false;
    }

    final User user = userOpt.get();
    if (user.inactive()) {
      userRepository.activate(user.id());
      eventPublisher.publishEvent(new AccountActivated(user));
    }
    return true;
  }

}
