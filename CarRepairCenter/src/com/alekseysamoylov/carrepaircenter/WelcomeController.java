package com.alekseysamoylov.carrepaircenter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/*
 * Created by Aleksey Samoylov on 29.12.2015.
 */
public class WelcomeController {

    @FXML
    private TextField settings;

    @FXML
    private Label label;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    public void buttonPressed(KeyEvent e) throws IOException {
        if (e.getCode().toString().equals("ENTER")) {
            clickOnButton();
        }
    }

    @FXML
    private void clickOnButton() throws IOException {
        if (settings.getText().length()<=1) {

                DataBaseConnection loginDB = new DataBaseConnection();
                try {
                    label.setText("Incorrect Login or Password");
                    label.setText(loginDB.loginDB());

                } catch (ClassNotFoundException e) {
                    label.setText("Incorrect Login or Password");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                    label.setText("Incorrect Login or Password");
                }

            if (!Objects.equals(label.getText(), "Incorrect Login or Password")) {
                Stage stage = (Stage) label.getScene().getWindow();

                new NewStage("mainStage.fxml");
                stage.close();
            }
        } else {
            DataBaseConnection loginDB = new DataBaseConnection();
            try {
                label.setText("Incorrect Login or Password");

                label.setText(loginDB.loginDB(login.getText(), password.getText(), settings.getText()));

            } catch (ClassNotFoundException | SQLException e) {
                label.setText("Incorrect Login or Password");
                e.printStackTrace();
            }
            if (!Objects.equals(label.getText(), "Incorrect Login or Password")) {
                Stage stage = (Stage) label.getScene().getWindow();

                new NewStage("mainStage.fxml");
                stage.close();
            }
        }
    }
}