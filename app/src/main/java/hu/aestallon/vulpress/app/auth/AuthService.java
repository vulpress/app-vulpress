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

package hu.aestallon.vulpress.app.auth;

import hu.aestallon.vulpress.app.rest.model.AuthenticationRequest;
import hu.aestallon.vulpress.app.rest.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Facilitates signing in to the application.
 *
 * <p>
 * Incoming login requests are authenticated with the configured authentication manager. If the
 * authentication attempt does not throw an appropriate exception, a JWT token is generated for the
 * user to identify them for subsequent requests.
 *
 * @author Szabolcs Bazil Papp
 * @see UserRepository
 * @see JwtService
 * @see AuthenticationManager
 * @since 2023-07-02
 */
@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository        userRepository;
  private final JwtService            jwtService;
  private final AuthenticationManager authenticationManager;

  /**
   * Signs in the user by generating a JWT token for them.
   *
   * <p>
   * If the authentication fails, this method will terminate early with the appropriate runtime
   * exception.
   *
   * @param authRequest the {@link AuthenticationRequest} containing the incoming raw username and
   *                    password, not null
   *
   * @return an {@link AuthenticationResponse} containing the generated JWT token for the user
   */
  public AuthenticationResponse login(AuthenticationRequest authRequest) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        authRequest.getUsername(),
        authRequest.getPassword()));
    final User user = userRepository
        .findOptionalByUsername(authRequest.getUsername())
        .orElseThrow(() -> new BadCredentialsException(""));
    final String jwt = jwtService.generateToken(user);
    return new AuthenticationResponse().token(jwt);
  }

}
