package hu.aestallon.vulpress.app.rest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * ArticleMoveRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ArticleMoveRequest {

  @JsonProperty("article")
  private String article;

  @JsonProperty("targetCategory")
  private String targetCategory;

  public ArticleMoveRequest article(String article) {
    this.article = article;
    return this;
  }

  /**
   * Get article
   * @return article
  */
  @NotNull 
  @Schema(name = "article", required = true)
  public String getArticle() {
    return article;
  }

  public void setArticle(String article) {
    this.article = article;
  }

  public ArticleMoveRequest targetCategory(String targetCategory) {
    this.targetCategory = targetCategory;
    return this;
  }

  /**
   * Get targetCategory
   * @return targetCategory
  */
  @NotNull 
  @Schema(name = "targetCategory", required = true)
  public String getTargetCategory() {
    return targetCategory;
  }

  public void setTargetCategory(String targetCategory) {
    this.targetCategory = targetCategory;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ArticleMoveRequest articleMoveRequest = (ArticleMoveRequest) o;
    return Objects.equals(this.article, articleMoveRequest.article) &&
        Objects.equals(this.targetCategory, articleMoveRequest.targetCategory);
  }

  @Override
  public int hashCode() {
    return Objects.hash(article, targetCategory);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ArticleMoveRequest {\n");
    sb.append("    article: ").append(toIndentedString(article)).append("\n");
    sb.append("    targetCategory: ").append(toIndentedString(targetCategory)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

