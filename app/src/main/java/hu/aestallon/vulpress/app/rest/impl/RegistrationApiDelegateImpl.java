package hu.aestallon.vulpress.app.rest.impl;

import hu.aestallon.vulpress.app.registration.RegistrationService;
import hu.aestallon.vulpress.app.rest.api.RegistrationApiDelegate;
import hu.aestallon.vulpress.app.rest.model.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationApiDelegateImpl implements RegistrationApiDelegate {

  private final RegistrationService registrationService;

  @Override
  public ResponseEntity<Void> registerAccount(AuthenticationRequest authenticationRequest) {
    return switch (registrationService.register(authenticationRequest)) {
      case ERR_INSUFFICIENT -> ResponseEntity.unprocessableEntity().build();
      case ERR_USERNAME_TAKEN -> ResponseEntity.status(HttpStatus.CONFLICT).build();
      case OK -> ResponseEntity.status(HttpStatus.CREATED).build();
    };
  }

  @Override
  public ResponseEntity<Void> verifyAccount(UUID registrationToken) {
    return (registrationService.verify(registrationToken))
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().build();
  }

}
