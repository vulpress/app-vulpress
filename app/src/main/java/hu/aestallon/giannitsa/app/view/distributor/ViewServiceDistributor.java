package hu.aestallon.giannitsa.app.view.distributor;

import hu.aestallon.giannitsa.app.rest.model.UiAction;
import hu.aestallon.giannitsa.app.view.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViewServiceDistributor implements InitializingBean {

  private final List<ViewService>        viewServices;
  private       Map<String, ViewService> viewServicesByName;

  @Override
  public void afterPropertiesSet() throws Exception {
    viewServicesByName = (viewServices == null)
        ? Collections.emptyMap()
        : viewServices.stream()
            .collect(Collectors.toMap(ViewService::name, Function.identity()));
  }

  public List<UiAction> actions(String viewName) throws UnknownViewNameException {
    return Optional
        .ofNullable(viewServicesByName.get(viewName))
        .map(ViewService::actions)
        .orElseThrow(UnknownViewNameException::new);
  }

  public static final class UnknownViewNameException extends Exception {

  }
}
