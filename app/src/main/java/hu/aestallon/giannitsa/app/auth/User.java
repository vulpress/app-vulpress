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


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * The primary (and only) User implementation.
 *
 * <p>
 * Instances of this class represent users of the application. This class extends
 * {@link UserDetails} to integrate with SpringBoot's user management and authentication systems.
 *
 * @author Szabolcs Bazil Papp
 * @see UserDetails
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @since 2023-07-02
 */
@Table("user_account")
@Data
@AllArgsConstructor
public class User implements UserDetails {
  private @Id                        Long    id;
  private final                      String  username;
  private final @Column("user_pw")   String  password;
  private final @Column("user_role") String  role;
  private final @Column("inactive")  boolean inactive;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority(role));
  }

  @Override
  public boolean isAccountNonExpired() {
    return !inactive;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !inactive;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !inactive;
  }

  @Override
  public boolean isEnabled() {
    return !inactive;
  }

}
