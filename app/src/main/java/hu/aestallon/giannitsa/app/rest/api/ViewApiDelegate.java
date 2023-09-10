package hu.aestallon.giannitsa.app.rest.api;

import hu.aestallon.giannitsa.app.rest.model.AppBarModel;
import hu.aestallon.giannitsa.app.rest.model.UiAction;
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
 * A delegate to be called by the {@link ViewApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface ViewApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /view/{viewName}/actions : Returns the available actions for a given view.
     * Returns the UiActions (usually represented as buttons) for a given view. 
     *
     * @param viewName  (required)
     * @return Ok (status code 200)
     *         or Unknown view name (status code 404)
     * @see ViewApi#getActions
     */
    default ResponseEntity<List<UiAction>> getActions(String viewName) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"colour\" : \"colour\", \"code\" : \"code\", \"icon\" : \"icon\", \"title\" : \"title\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /view/app : Returns the state of the app bar.
     * Returns the state of the app bar, including the title of the application, the logged-in status (for rendering the profile drop-down menu), and the available UiActions in the hamburger menu. 
     *
     * @return Ok (status code 200)
     * @see ViewApi#getAppBar
     */
    default ResponseEntity<AppBarModel> getAppBar() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"availableCategories\" : [ { \"colour\" : \"colour\", \"code\" : \"code\", \"icon\" : \"icon\", \"title\" : \"title\" }, { \"colour\" : \"colour\", \"code\" : \"code\", \"icon\" : \"icon\", \"title\" : \"title\" } ], \"appName\" : \"appName\", \"loggedIn\" : false }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
