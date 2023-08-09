/*
 * Copyright 2023 Szabolcs Bazil Papp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.aestallon.giannitsa.app.controller;

import hu.aestallon.giannitsa.app.auth.AuthService;
import hu.aestallon.giannitsa.app.rest.api.AuthApiDelegate;
import hu.aestallon.giannitsa.app.rest.model.AuthenticationRequest;
import hu.aestallon.giannitsa.app.rest.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthApiDelegateImpl implements AuthApiDelegate {

  private final AuthService authService;

  @Override
  public ResponseEntity<Void> isAuthenticated() {
    try {
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @Override
  public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest authenticationRequest) {
    try {
      return ResponseEntity.ok(authService.login(authenticationRequest));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

}
