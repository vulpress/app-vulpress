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


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Table("account_user")
public record User(
    @Id Long id,
    @Column("username") String username,
    @Column("mail_addr") String email,
    @Column("user_pw") String password,
    @Column("user_role") String role,
    @Column("inactive") boolean inactive,
    @Column("reg_token") UUID registrationToken,
    @Column("token_exp") LocalDateTime tokenExpiresAt
) implements UserDetails {

  public User(String username, String password, String role, boolean inactive) {
    this(null, username, username, password, role, inactive, null, null);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority(role));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
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

  public User token(TemporalAmount validFor) {
    return new User(id, username, email, password, role, inactive, UUID.randomUUID(),
        LocalDateTime.now().plus(validFor));
  }

}
