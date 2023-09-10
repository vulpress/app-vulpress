package hu.aestallon.giannitsa.app.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.aestallon.giannitsa.app.config.IntegrationTestConfig;
import hu.aestallon.giannitsa.app.rest.model.AuthenticationRequest;
import hu.aestallon.giannitsa.app.rest.model.AuthenticationResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootApplication
@SpringBootTest(
    classes = {
        IntegrationTestConfig.class
    },
    properties = {
        "giannitsa-db.settings.h2.username=test-sa",
        "giannitsa-db.settings.h2.password=test-sa",
        "giannitsa-db.settings.h2.dbase-url=jdbc:h2:file:./test;MODE=PostgreSQL;"
        + "DATABASE_TO_LOWER=TRUE;"
        + "CASE_INSENSITIVE_IDENTIFIERS=TRUE;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE"
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles({"bootstrap", "h2"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WebSecurityTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityTest.class);

  @Autowired
  RestTemplate restTemplate;
  @Autowired
  ObjectMapper objectMapper;
  @LocalServerPort
  private int    serverPort;
  @Value("${openapi.giannitsaWebAppBFF.base-path:/}")
  private String basePath;

  private String jwtToken;

  private <T> HttpEntity<T> requestEntity() {
    return requestEntity(null);
  }

  private <T> HttpEntity<T> requestEntity(T body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    if (jwtToken != null && !jwtToken.isBlank()) {
      headers.setBearerAuth(jwtToken);
    }
    return new HttpEntity<>(body, headers);
  }

  private String endpointPath(String endpoint) {
    return new StringBuilder("http://localhost:")
        .append(serverPort)
        .append('/')
        .append(basePath)
        .append(endpoint)
        .toString();
  }

  private <T> T post(String endpoint, Object payload, Class<T> responseType) {
    LOGGER.debug("Invoking [ {} ] with payload [ {} ]", endpoint, payload);
    final ResponseEntity<T> response = restTemplate.exchange(
        endpointPath(endpoint),
        HttpMethod.POST,
        requestEntity(payload),
        responseType
    );
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).satisfies(
        code -> assertThat(code.is2xxSuccessful()).isTrue());

    if (Void.class == responseType) {
      return null;
    }
    return response.getBody();
  }

  private <T> T get(String endpoint, Class<T> responseType) {
    final ResponseEntity<T> response = restTemplate.exchange(
        endpointPath(endpoint),
        HttpMethod.GET,
        requestEntity(),
        responseType
    );
    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).satisfies(
        code -> assertThat(code.is2xxSuccessful()).isTrue());

    if (Void.class == responseType) {
      return null;
    }
    return response.getBody();
  }

  @Test
  @Order(0)
  void contextLoads() {}

  @Test
  @Order(1)
  void requestingAuthenticationStatusAnonymously_returnsForbiddenStatus() {
    assertThrows(HttpClientErrorException.Forbidden.class, () -> get("/auth", Void.class));
  }

  @Test
  @Order(2)
  void requestingAuthenticationStatusWithBadToken_returnsForbiddenStatus() {
    jwtToken = "something-invalid";
    assertThrows(HttpClientErrorException.Forbidden.class, () -> get("/auth", Void.class));
    jwtToken = null;
  }

  @Test
  @Order(3)
  void attemptingToLogInWithInvalidCredentials_resultsInUnauthorizedStatus() {
    assertThrows(HttpClientErrorException.Unauthorized.class, () -> post(
        "/auth/login",
        new AuthenticationRequest()
            .username("admin")
            .password("admin-bad"),
        AuthenticationResponse.class));
  }

  @Test
  @Order(4)
  void attemptingToLogInWithCorrectCredentials_returnsAnResponseWithAToken() {
    final AuthenticationResponse response = post(
        "/auth/login",
        new AuthenticationRequest()
            .username("admin")
            .password("admin"),
        AuthenticationResponse.class);
    assertThat(response).isNotNull();

    String token = response.getToken();
    assertThat(token).isNotNull().isNotBlank();

    jwtToken = token;
  }

  @Test
  @Order(5)
  void requestingAuthenticationStatusWithValidToken_resultsInSuccessfulExchange() {
    assertDoesNotThrow(() -> get("/auth", Void.class));
  }
}
