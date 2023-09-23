package hu.aestallon.vulpress.app.rest.api;

import hu.aestallon.vulpress.app.rest.model.AuthenticationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

/**
 * A delegate to be called by the {@link RegistrationApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface RegistrationApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /registration : Submits a registration.
     * ... 
     *
     * @param authenticationRequest  (required)
     * @return Registration accepted (status code 201)
     *         or Username taken (status code 409)
     *         or Inappropriate properties (status code 422)
     * @see RegistrationApi#registerAccount
     */
    default ResponseEntity<Void> registerAccount(AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /registration : Activates a registration.
     * ... 
     *
     * @param registrationToken  (required)
     * @return Idempotent OK.  Attempting to verify a valid registration token always returns with this response.  (status code 200)
     *         or Unknown registration (status code 400)
     * @see RegistrationApi#verifyAccount
     */
    default ResponseEntity<Void> verifyAccount(java.util.UUID registrationToken) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
