package hu.aestallon.giannitsa.app.rest.api;

import hu.aestallon.giannitsa.app.rest.model.ApiError;
import hu.aestallon.giannitsa.app.rest.model.ArticleDetail;
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
 * A delegate to be called by the {@link ArticlesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface ArticlesApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /articles/{article} : Load the contents of an article
     * ... 
     *
     * @param article  (required)
     * @return Ok (status code 200)
     *         or Not found (status code 404)
     *         or Unauthorized (status code 401)
     * @see ArticlesApi#getArticle
     */
    default ResponseEntity<ArticleDetail> getArticle(String article) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"code\" : \"code\", \"author\" : \"author\", \"title\" : \"title\", \"issueDate\" : \"2000-01-23\", \"paragraphs\" : [ { \"image\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\", \"title\" : \"title\" }, { \"image\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"text\" : \"text\", \"title\" : \"title\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
