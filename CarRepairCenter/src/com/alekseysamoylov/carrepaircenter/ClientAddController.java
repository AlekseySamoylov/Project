package com.alekseysamoylov.carrepaircenter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ClientAddController {

    @FXML
    private Label label;

    @FXML
    private TextField firstName;

    @FXML
    private TextField secondName;

    @FXML
    private TextField phone;

    @FXML
    private TextArea other;

    @FXML
    private void clickBack() {
        Stage stage = (Stage) phone.getScene().getWindow();
        stage.close();
        try {
            new NewStage("clientInOrder.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickBack2() {
        Stage stage = (Stage) phone.getScene().getWindow();
        stage.close();
        try {
            new NewStage("clients.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickAddClient() throws SQLException, ClassNotFoundException {


if(clientAdd()) {
    Stage stage = (Stage) phone.getScene().getWindow();
    stage.close();
    try {
        new NewStage("clientInOrder.fxml");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    }

    @FXML
    private void clickAddClient2() throws SQLException, ClassNotFoundException {


        if(clientAdd()) {
            Stage stage = (Stage) phone.getScene().getWindow();
            stage.close();
            try {
                new NewStage("clients.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean clientAdd() throws SQLException, ClassNotFoundException {
        if ((firstName.getText().length() >= 3) && (secondName.getText().length() >= 3)) {
            Connection conn = DataBaseConnection.connectionOpen();
            String sql = "INSERT INTO clients (firstName, secondname, phone, other) VALUES (?, ?, ?, ?)";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setString(1, firstName.getText());
            prepare.setString(2, secondName.getText());
            prepare.setString(3, phone.getText());
            prepare.setString(4, other.getText());
            prepare.executeUpdate();


           return true;
        } else {
            label.setText("Введены некорректные дынные");
            return false;
        }
    }
}


