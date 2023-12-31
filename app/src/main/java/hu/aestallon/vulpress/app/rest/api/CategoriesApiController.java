package hu.aestallon.vulpress.app.rest.api;

import hu.aestallon.vulpress.app.rest.model.ApiError;
import hu.aestallon.vulpress.app.rest.model.ArticleDetail;
import hu.aestallon.vulpress.app.rest.model.ArticlePreview;
import hu.aestallon.vulpress.app.rest.model.Category;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.vulpress.base-path:}")
public class CategoriesApiController implements CategoriesApi {

    private final CategoriesApiDelegate delegate;

    public CategoriesApiController(@Autowired(required = false) CategoriesApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new CategoriesApiDelegate() {});
    }

    @Override
    public CategoriesApiDelegate getDelegate() {
        return delegate;
    }

}
