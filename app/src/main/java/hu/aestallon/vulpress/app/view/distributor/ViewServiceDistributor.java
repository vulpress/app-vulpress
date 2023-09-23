package hu.aestallon.vulpress.app.view.distributor;

import hu.aestallon.vulpress.app.rest.model.UiAction;
import hu.aestallon.vulpress.app.view.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

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

  public List<UiAction> actions(String viewName, String pageName) throws UnknownViewNameException {
    return Optional
        .ofNullable(viewServicesByName.get(viewName))
        .map(service -> service.actions(pageName))
        .orElseThrow(UnknownViewNameException::new);
  }

  public List<UiAction> actions(String viewName) throws UnknownViewNameException {
    return actions(viewName, null);
  }

  public static final class UnknownViewNameException extends Exception {}

}
