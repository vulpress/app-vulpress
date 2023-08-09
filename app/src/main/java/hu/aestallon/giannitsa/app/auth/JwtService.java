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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {

  @Value("${jwt.secret:secret}")
  private String secretKey;
  @Value("${jwt.timeout:10}")
  private int    timeout;
  @Value("${jwt.cookie:secret}")
  private String jwtCookie;

  /**
   * Extracts the subject contained in the JWT token.
   *
   * <p>
   * In this implementation, the subject is guaranteed to be a username.
   *
   * @param token the {@code String} JWT token to examine
   *
   * @return the {@code String} subject contained in the token
   */
  public String extractSubject(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claims != null
        ? claimsResolver.apply(claims)
        : null;
  }

  private Claims extractAllClaims(String token) {
    try {
      return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      // log.debug("The JWT token has expired!", e);
    } catch (Exception e) {
      // log.debug("Error when parsing JWT token", e);
    }
    return null;
  }

  private boolean isTokenExpired(String token) {
    Date expiration = extractExpiration(token);
    return expiration == null || expiration.before(new Date());
  }

  /**
   * Generates a new JWT token enclosing the user's username.
   *
   * @param user the {@link User} the token is generated for, not null
   *
   * @return the generated {@code String} token
   */
  public String generateToken(User user) {
    return createToken(user.getUsername());
  }

  private String createToken(String subject) {
    return createToken(subject, null);
  }

  private String createToken(String subject, OffsetDateTime expiration) {
    Objects.requireNonNull(subject, "Subject can not be null!");

    if (expiration == null) {
      expiration = OffsetDateTime.now().plusHours(timeout);
    }
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(OffsetDateTime.MAX.equals(expiration)
            ? new Date(Long.MAX_VALUE)
            : new Date(expiration.toInstant().toEpochMilli()))
        .setHeaderParam("uuid", UUID.randomUUID()) // make every created token identical
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  /**
   * Validates the specified token against the provided user.
   *
   * @param token the {@code String} JWT token to inspect, not null
   * @param user  the {@code User} the token supposedly belongs to, may be null
   *
   * @return true if the token correctly identifies the provided user, false if the user is null,
   * the token is invalid or expired, or if the token belongs to a different user
   */
  public boolean validateToken(String token, UserDetails user) {
    final String username = extractSubject(token);
    return username != null && user != null && username.equals(user.getUsername())
           && !isTokenExpired(token);
  }

  /**
   * Extracts the JWT token from an incoming HTTP request (if any).
   *
   * @param request the incoming {@link HttpServletRequest} to be examined, not null
   *
   * @return the {@code String} JWT token extracted from the {@code Authorization} header element,
   * or null if there wasn't any
   */
  public String getJwtTokenFromRequest(HttpServletRequest request) {
    String jwt = null;

    final String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
    } else {
      // allow for the JWT token to travel as a cookie:
      Cookie[] cookies = request.getCookies();
      if (cookies != null) {
        for (int i = 0; i < cookies.length; i++) {
          Cookie cookie = cookies[i];
          if (cookie != null && jwtCookie.equals(cookie.getName())) {
            jwt = cookie.getValue();
            break;
          }
        }
      }
    }
    return jwt;
  }

}
