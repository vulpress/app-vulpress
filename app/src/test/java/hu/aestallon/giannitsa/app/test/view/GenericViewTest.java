package hu.aestallon.giannitsa.app.test.view;

import hu.aestallon.giannitsa.app.config.BusinessLogicTest;
import hu.aestallon.giannitsa.app.view.distributor.ViewServiceDistributor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@BusinessLogicTest
class GenericViewTest {

  @Autowired
  ViewServiceDistributor viewServiceDistributor;

  @Test
  void requestingActionsForUnknownViewName_throwsException() {
    assertThrows(
      ViewServiceDistributor.UnknownViewNameException.class,
        () -> viewServiceDistributor.actions("something gibberish"));
  }
}
