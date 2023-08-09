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

package hu.aestallon.giannitsa.app.auth;

import hu.aestallon.giannitsa.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Enables retrieval of users.
 *
 * <p>
 * This class implements {@link UserDetailsService} to integrate with SpringBoot's user and
 * authentication management systems.
 *
 * <p>
 * Additional methods are provided to retrieve the user currently held in a given control flow's
 * {@link SecurityContext}.
 *
 * @author Szabolcs Bazil Papp
 * @see User
 * @see UserDetailsService
 * @see SecurityContext
 * @see SecurityContextHolder
 * @since 2023-07-02
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findOptionalByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("unknown user"));
  }

  /**
   * Retrieves the user from the ongoing request's {@link SecurityContext}.
   *
   * @return the {@link User} currently authenticated in the running code's context, or null if
   * there isn't any
   */
  public User currentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return null;
    }

    if (!(authentication instanceof UsernamePasswordAuthenticationToken token)) {
      return null;
    }

    Object principal = token.getPrincipal();
    System.out.println("Principal+ " + principal);
    if (!(principal instanceof User user)) {
      return null;
    }
    return user;
  }

  /**
   * Checks whether the current user is an administrator or not.
   *
   * @return true, if the user in the request's {@link SecurityContext} is an administrator, false,
   * if there is no user in the {@code SecurityContext}, or there is one but without administrator
   * privileges
   */
  public boolean isCurrentUserAdmin() {
    final User user = currentUser();
    return user != null && "ADMIN".equalsIgnoreCase(user.getRole());
  }

}
