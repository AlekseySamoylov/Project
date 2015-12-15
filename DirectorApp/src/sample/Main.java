package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("г. Пермь, ул. 2-я Новгородская, 108. Автосервис \"КАМЕНКА\"");
        primaryStage.setScene(new Scene(root, 730, 660));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}