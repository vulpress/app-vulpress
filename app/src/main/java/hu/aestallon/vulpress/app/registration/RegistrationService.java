package hu.aestallon.vulpress.app.registration;

import hu.aestallon.vulpress.app.auth.User;
import hu.aestallon.vulpress.app.auth.UserRepository;
import hu.aestallon.vulpress.app.auth.UserService;
import hu.aestallon.vulpress.app.event.AccountActivated;
import hu.aestallon.vulpress.app.event.RegistrationAccepted;
import hu.aestallon.vulpress.app.event.RegistrationRejected;
import hu.aestallon.vulpress.app.rest.model.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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


  @Value("${vulpress.reg.password.length-min:8}")
  private int      minPasswordLength;
  @Value("${vulpress.reg.token-validity:PT3H}")
  private Duration verificationTokenValidity;


  public RegistrationResult register(AuthenticationRequest authenticationRequest) {
    log.debug("RegistrationService.register invoked for {}", authenticationRequest);

    final String username = authenticationRequest.getUsername().trim();
    final String rawPassword = authenticationRequest.getPassword().trim();
    if (!SIMPLE_MAIL_PTRN.matcher(username).matches()
        || rawPassword.isBlank()
        || rawPassword.length() < minPasswordLength) {
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
        .token(verificationTokenValidity);
    user = userRepository.save(user);
    log.debug("User created: {}", user);

    eventPublisher.publishEvent(new RegistrationAccepted(user));
    return RegistrationResult.OK;
  }

  public boolean verify(UUID registrationToken) {
    log.debug("Verifying registration: [ {} ]", registrationToken);
    final var userOpt = userRepository.findOptionalByToken(registrationToken);
    if (userOpt.isEmpty()) {
      log.warn("Unknown registration token [ {} ]", registrationToken);
      return false;
    }

    final User user = userOpt.get();
    if (user.inactive()) {
      log.debug("User has not yet been activated: {} ACTIVATING...", user);
      userRepository.activate(user.id());
      eventPublisher.publishEvent(new AccountActivated(user));
    }
    log.debug("Registration verified for user: {}", user);
    return true;
  }

}
