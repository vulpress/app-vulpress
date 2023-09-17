package hu.aestallon.giannitsa.app.view;

import hu.aestallon.giannitsa.app.rest.model.UiAction;

import java.util.List;

public interface ViewService {

  String name();

  List<UiAction> actions(String identifier);

}
