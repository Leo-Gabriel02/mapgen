import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("UI.fxml"));
        primaryStage.setTitle("Map Generator");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.setMaximized(true);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
