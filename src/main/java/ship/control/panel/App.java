package ship.control.panel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shipControlPanelView.fxml"));
        AnchorPane mainView = fxmlLoader.load();
        Scene scene = new Scene(mainView);
        stage.setScene(scene);
        stage.show();
    }
}
