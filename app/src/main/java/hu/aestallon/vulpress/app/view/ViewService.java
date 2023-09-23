package hu.aestallon.vulpress.app.view;

import hu.aestallon.vulpress.app.rest.model.UiAction;

import java.util.List;

public interface ViewService {

  String name();

  List<UiAction> actions(String identifier);

}
