package com.alekseysamoylov.carrepaircenter;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Aleksey Samoylov on 29.12.2015.
 */
public class NewStage {
    NewStage(String fxmlName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlName));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                try {
                    DataBaseConnection.connectionClose();
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
