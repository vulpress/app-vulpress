package hu.aestallon.vulpress.app.rest.api;

import hu.aestallon.vulpress.app.rest.model.ApiError;
import hu.aestallon.vulpress.app.rest.model.ArticleDetail;
import hu.aestallon.vulpress.app.rest.model.ArticleMoveRequest;
import hu.aestallon.vulpress.app.rest.model.ArticlePreview;
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
     * DELETE /articles/{article} : Delete an article
     * ... 
     *
     * @param article  (required)
     * @return Ok (status code 200)
     *         or Not found (status code 404)
     *         or Unauthorized (status code 401)
     * @see ArticlesApi#deleteArticle
     */
    default ResponseEntity<Void> deleteArticle(String article) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /articles : Searches articles
     * Finds articles matching the filter criteria provided as query parameters. 
     *
     * @param contains  (required)
     * @return Ok (status code 200)
     * @see ArticlesApi#findArticles
     */
    default ResponseEntity<List<ArticlePreview>> findArticles(String contains) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"path\", \"thumbnail\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"code\" : \"code\", \"author\" : \"author\", \"description\" : \"description\", \"title\" : \"title\", \"issueDate\" : \"2000-01-23\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

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

    /**
     * POST /articles : Moves an article
     * ... 
     *
     * @param articleMoveRequest  (optional)
     * @return Ok (status code 200)
     *         or Not found (status code 404)
     *         or Unauthorized (status code 401)
     * @see ArticlesApi#moveArticle
     */
    default ResponseEntity<Void> moveArticle(ArticleMoveRequest articleMoveRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
