package hu.aestallon.giannitsa.app.rest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import hu.aestallon.giannitsa.app.rest.model.Paragraph;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * ArticleDetail
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class ArticleDetail {

  @JsonProperty("code")
  private String code;

  @JsonProperty("title")
  private String title;

  @JsonProperty("paragraphs")
  @Valid
  private java.util.List<Paragraph> paragraphs = new java.util.ArrayList<>();

  public ArticleDetail code(String code) {
    this.code = code;
    return this;
  }

  /**
   * URL-safe unique identifier. 
   * @return code
  */
  @NotNull 
  @Schema(name = "code", description = "URL-safe unique identifier. ", required = true)
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ArticleDetail title(String title) {
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

  public ArticleDetail paragraphs(java.util.List<Paragraph> paragraphs) {
    this.paragraphs = paragraphs;
    return this;
  }

  public ArticleDetail addParagraphsItem(Paragraph paragraphsItem) {
    this.paragraphs.add(paragraphsItem);
    return this;
  }

  /**
   * Get paragraphs
   * @return paragraphs
  */
  @NotNull @Valid 
  @Schema(name = "paragraphs", required = true)
  public java.util.List<Paragraph> getParagraphs() {
    return paragraphs;
  }

  public void setParagraphs(java.util.List<Paragraph> paragraphs) {
    this.paragraphs = paragraphs;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ArticleDetail articleDetail = (ArticleDetail) o;
    return Objects.equals(this.code, articleDetail.code) &&
        Objects.equals(this.title, articleDetail.title) &&
        Objects.equals(this.paragraphs, articleDetail.paragraphs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, title, paragraphs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ArticleDetail {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    paragraphs: ").append(toIndentedString(paragraphs)).append("\n");
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

