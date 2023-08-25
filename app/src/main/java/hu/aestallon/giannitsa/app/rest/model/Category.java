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
 * Category
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class Category {

  @JsonProperty("code")
  private String code;

  @JsonProperty("displayValue")
  private String displayValue;

  public Category code(String code) {
    this.code = code;
    return this;
  }

  /**
   * URL-safe unique identifier. 
   * @return code
  */
  @NotNull 
  @Schema(name = "code", example = "homilies", description = "URL-safe unique identifier. ", required = true)
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Category displayValue(String displayValue) {
    this.displayValue = displayValue;
    return this;
  }

  /**
   * Get displayValue
   * @return displayValue
  */
  @NotNull 
  @Schema(name = "displayValue", example = "Homilies", required = true)
  public String getDisplayValue() {
    return displayValue;
  }

  public void setDisplayValue(String displayValue) {
    this.displayValue = displayValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category category = (Category) o;
    return Objects.equals(this.code, category.code) &&
        Objects.equals(this.displayValue, category.displayValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, displayValue);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Category {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    displayValue: ").append(toIndentedString(displayValue)).append("\n");
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

