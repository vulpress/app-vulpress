package hu.aestallon.giannitsa.app.rest.model;

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
 * ArticlePreview
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ArticlePreview {

  @JsonProperty("code")
  private String code;

  @JsonProperty("title")
  private String title;

  @JsonProperty("description")
  private String description;

  @JsonProperty("thumbnail")
  private java.util.UUID thumbnail;

  public ArticlePreview code(String code) {
    this.code = code;
    return this;
  }

  /**
   * URL-safe unique identifier. 
   * @return code
  */
  
  @Schema(name = "code", description = "URL-safe unique identifier. ", required = false)
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ArticlePreview title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */
  @NotNull 
  @Schema(name = "title", required = true)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public ArticlePreview description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  @NotNull 
  @Schema(name = "description", required = true)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ArticlePreview thumbnail(java.util.UUID thumbnail) {
    this.thumbnail = thumbnail;
    return this;
  }

  /**
   * The unique identifier of the image to be as thumbnail for the article. 
   * @return thumbnail
  */
  @Valid 
  @Schema(name = "thumbnail", description = "The unique identifier of the image to be as thumbnail for the article. ", required = false)
  public java.util.UUID getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(java.util.UUID thumbnail) {
    this.thumbnail = thumbnail;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ArticlePreview articlePreview = (ArticlePreview) o;
    return Objects.equals(this.code, articlePreview.code) &&
        Objects.equals(this.title, articlePreview.title) &&
        Objects.equals(this.description, articlePreview.description) &&
        Objects.equals(this.thumbnail, articlePreview.thumbnail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, title, description, thumbnail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ArticlePreview {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    thumbnail: ").append(toIndentedString(thumbnail)).append("\n");
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

