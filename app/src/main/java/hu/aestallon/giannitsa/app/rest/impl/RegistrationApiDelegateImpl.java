package hu.aestallon.giannitsa.app.rest.impl;

import hu.aestallon.giannitsa.app.rest.api.RegistrationApiDelegate;
import hu.aestallon.giannitsa.app.rest.model.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationApiDelegateImpl implements RegistrationApiDelegate {

  @Override
  public ResponseEntity<Void> registerAccount(AuthenticationRequest authenticationRequest) {
    return RegistrationApiDelegate.super.registerAccount(authenticationRequest);
  }

  @Override
  public ResponseEntity<Void> verifyAccount(UUID registrationToken) {
    return RegistrationApiDelegate.super.verifyAccount(registrationToken);
  }

}
