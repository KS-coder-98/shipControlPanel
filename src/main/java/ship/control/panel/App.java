package ship.control.panel;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.TickLabelOrientation;
import eu.hansolo.medusa.skins.ModernSkin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ship.control.panel.model.GaugeFuel;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shipControlPanelView.fxml"));
        AnchorPane mainView = fxmlLoader.load();
        Scene scene = new Scene(mainView);
//        Pane fuelPane = (Pane) scene.lookup("#fuelGaugePane");

//        Gauge gaugeFuel = GaugeFuel.createGaugeFuel();

//        fuelPane.getChildren().addAll(gaugeFuel);
        stage.setScene(scene);
        stage.show();
    }
}
