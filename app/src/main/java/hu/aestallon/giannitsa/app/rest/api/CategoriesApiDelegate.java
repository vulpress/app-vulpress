package hu.aestallon.giannitsa.app.rest.api;

import hu.aestallon.giannitsa.app.rest.model.ArticleDetail;
import hu.aestallon.giannitsa.app.rest.model.ArticlePreview;
import hu.aestallon.giannitsa.app.rest.model.Category;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
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
 * A delegate to be called by the {@link CategoriesApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface CategoriesApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /categories : Create a new category
     * Creates a new, empty category. The category&#39;s URL-safe unique code is generated server side, and must be unique. 
     *
     * @param category  (optional)
     * @return Category has been created. (status code 201)
     *         or Insufficient authorization for category creation. (status code 401)
     *         or Category already exists. (status code 409)
     * @see CategoriesApi#createCategory
     */
    default ResponseEntity<Category> createCategory(Category category) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"code\" : \"homilies\", \"description\" : \"description\", \"title\" : \"Homilies\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /categories/{category} : Delete a category
     * Deletes a category, moving its contents to the archived category. The archived category may not be deleted. 
     *
     * @param category  (required)
     * @return Category deleted (status code 200)
     *         or Insufficient authorization to delete category. (status code 401)
     * @see CategoriesApi#deleteCategory
     */
    default ResponseEntity<Void> deleteCategory(String category) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /categories/{category}/{page} : Return the Nth page of a categories articles
     * ... 
     *
     * @param category  (required)
     * @param page  (required)
     * @return Ok (status code 200)
     *         or Insufficient authorization to fetch category contents. (status code 401)
     *         or Unknown category (status code 404)
     * @see CategoriesApi#getArticles
     */
    default ResponseEntity<List<ArticlePreview>> getArticles(String category,
        Integer page) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"thumbnail\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"code\" : \"code\", \"description\" : \"description\", \"title\" : \"title\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /categories/{category} : Return the first page of articles in a category
     * ... 
     *
     * @param category  (required)
     * @return Ok (status code 200)
     *         or Insufficient authorization to fetch category contents. (status code 401)
     *         or Unknown category (status code 404)
     * @see CategoriesApi#getRecentArticles
     */
    default ResponseEntity<List<ArticlePreview>> getRecentArticles(String category) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"thumbnail\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"code\" : \"code\", \"description\" : \"description\", \"title\" : \"title\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /categories : List all categories
     * Returns a list of categories available with the provided authorisation (regular and anonymous users may only receive a subset of categories available to administrators). 
     *
     * @return Ok (status code 200)
     * @see CategoriesApi#listCategories
     */
    default ResponseEntity<List<Category>> listCategories() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"code\" : \"homilies\", \"description\" : \"description\", \"title\" : \"Homilies\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /categories/{category} : Creates a new article in this category
     * ... 
     *
     * @param category  (required)
     * @param title The desired title of the article  (required)
     * @param issued The date for which this article is issued, may be null.  (required)
     * @param author The name of the article&#39;s author. If null, the uploader is considered the author.  (required)
     * @param description Succinct description for the article.  (required)
     * @param documentFile  (required)
     * @return Created (status code 201)
     * @see CategoriesApi#uploadArticle
     */
    default ResponseEntity<ArticleDetail> uploadArticle(String category,
        String title,
        LocalDate issued,
        String author,
        String description,
        MultipartFile documentFile) {
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
