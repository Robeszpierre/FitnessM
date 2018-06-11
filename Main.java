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
import java.util.Locale;

public class Main extends Application {

    DB db=null;

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));

        Locale.setDefault(new Locale("hu", "HUN"));

        Scene scene = new Scene(root);
        stage.setTitle("FitnessM");
        stage.setMaximized(true);
        stage.setScene(scene);

        scene.getStylesheets().add(this.getClass().getResource("view.css").toExternalForm());
        stage.show();

        db=new DB();
    }

    @Override
    public void stop(){
        db.shutDownDB();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
