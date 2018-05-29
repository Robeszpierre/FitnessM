package FintessM;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.text.View;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("FitnessM");
        stage.setWidth(1920);
        stage.setHeight(1000);
        stage.setScene(scene);

        scene.getStylesheets().add(this.getClass().getResource("view.css").toExternalForm());
        stage.show();

        DB db=new DB();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
