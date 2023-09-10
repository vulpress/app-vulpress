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

package hu.aestallon.giannitsa.app.config;

import hu.aestallon.giannitsa.app.auth.JwtRequestFilter;
import hu.aestallon.giannitsa.app.auth.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

/**
 * The web security configuration for the application.
 *
 * <p>
 * By default, every request target requires authentication. The {@link JwtRequestFilter} is
 * inserted into the filter chain to authenticate users who are arriving with a valid JWT token, and
 * thus can pass the later username-password authentication filter.
 *
 * <p>
 * Authentication requirements are lifted for public assets, the index.html and various common FE
 * artifacts required for clients to operate. Routes {@code /login} and the root is also
 * unrestricted - the Vue client is expected to reroute from the root to {@code /login} if it cannot
 * establish its own authenticated status.
 *
 * <p>
 * The only API endpoint left unprotected is the one used for signing in (as users wishing to sign
 * in are obviously unauthenticated).
 *
 * @author Szabolcs Bazil Papp
 * @since 2023-07-02
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class GiannitsaWebSecurityConfig {

  private final UserService userService;
  private final JwtRequestFilter jwtRequestFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(requests -> requests
            .requestMatchers(
                "/api/auth/login",
                "/favicon.ico",
                "/favicon.png",
                "/styles.css.map",
                "/polyfills.js.map",
                "/runtime.js.map",
                "/vendor.js.map",
                "/main.js.map",
                "/index.html",
                "/assets/**",
                "/**",
                "/main/**",
                "/login", "/h2-console/**").permitAll()
            .anyRequest().authenticated())
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/main", true)
            .permitAll())
        .logout(LogoutConfigurer::permitAll)
        .sessionManagement(m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(this.authenticationProvider())
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(ex -> ex
            .accessDeniedHandler(this::handleException)
            .addObjectPostProcessor(new ObjectPostProcessor<ExceptionTranslationFilter>() {

              @Override
              public <O extends ExceptionTranslationFilter> O postProcess(O object) {
                object.setAuthenticationTrustResolver(new RejectingAuthTrustResolver());
                return object;
              }

            }))
        .build();
  }

  private void handleException(HttpServletRequest req, HttpServletResponse res,
                               AccessDeniedException ex) throws IOException, ServletException {
    res.setStatus(HttpStatus.FORBIDDEN.value());
  }

  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
  throws Exception {
    return config.getAuthenticationManager();
  }

  private static class RejectingAuthTrustResolver implements AuthenticationTrustResolver {
    @Override
    public boolean isAnonymous(Authentication authentication) {
      return false;
    }

    @Override
    public boolean isRememberMe(Authentication authentication) {
      return false;
    }
  }

}
