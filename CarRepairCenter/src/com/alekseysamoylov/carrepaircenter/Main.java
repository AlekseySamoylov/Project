package com.alekseysamoylov.carrepaircenter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
Created by Aleksey Samoylov on 31.12.2015.
 */
public class Main  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
        primaryStage.setTitle("Автосервис");
        primaryStage.setScene(new Scene(root, 500, 250));
        primaryStage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }
}
