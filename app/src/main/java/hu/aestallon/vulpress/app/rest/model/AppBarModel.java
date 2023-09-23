package hu.aestallon.vulpress.app.rest.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import hu.aestallon.vulpress.app.rest.model.UiAction;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * AppBarModel
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class AppBarModel {

  @JsonProperty("appName")
  private String appName;

  @JsonProperty("loggedIn")
  private Boolean loggedIn = false;

  @JsonProperty("availableCategories")
  @Valid
  private java.util.List<UiAction> availableCategories = null;

  public AppBarModel appName(String appName) {
    this.appName = appName;
    return this;
  }

  /**
   * Get appName
   * @return appName
  */
  
  @Schema(name = "appName", required = false)
  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public AppBarModel loggedIn(Boolean loggedIn) {
    this.loggedIn = loggedIn;
    return this;
  }

  /**
   * Get loggedIn
   * @return loggedIn
  */
  
  @Schema(name = "loggedIn", required = false)
  public Boolean getLoggedIn() {
    return loggedIn;
  }

  public void setLoggedIn(Boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  public AppBarModel availableCategories(java.util.List<UiAction> availableCategories) {
    this.availableCategories = availableCategories;
    return this;
  }

  public AppBarModel addAvailableCategoriesItem(UiAction availableCategoriesItem) {
    if (this.availableCategories == null) {
      this.availableCategories = new java.util.ArrayList<>();
    }
    this.availableCategories.add(availableCategoriesItem);
    return this;
  }

  /**
   * Get availableCategories
   * @return availableCategories
  */
  @Valid 
  @Schema(name = "availableCategories", required = false)
  public java.util.List<UiAction> getAvailableCategories() {
    return availableCategories;
  }

  public void setAvailableCategories(java.util.List<UiAction> availableCategories) {
    this.availableCategories = availableCategories;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppBarModel appBarModel = (AppBarModel) o;
    return Objects.equals(this.appName, appBarModel.appName) &&
        Objects.equals(this.loggedIn, appBarModel.loggedIn) &&
        Objects.equals(this.availableCategories, appBarModel.availableCategories);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appName, loggedIn, availableCategories);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppBarModel {\n");
    sb.append("    appName: ").append(toIndentedString(appName)).append("\n");
    sb.append("    loggedIn: ").append(toIndentedString(loggedIn)).append("\n");
    sb.append("    availableCategories: ").append(toIndentedString(availableCategories)).append("\n");
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

