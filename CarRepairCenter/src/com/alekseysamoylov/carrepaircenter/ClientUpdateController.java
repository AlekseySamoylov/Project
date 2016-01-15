package com.alekseysamoylov.carrepaircenter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/*
  Created by Aleksey Samoylov on 31/12/15.
 */
public class ClientUpdateController implements Initializable{
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
            new NewStage("сlientInOrder.fxml");
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
    private void clickAddClient() throws ClassNotFoundException, SQLException {

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
    private void clickAddClient2() throws ClassNotFoundException, SQLException {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
try {
    Connection conn = DataBaseConnection.connectionOpen();
    String sql = "SELECT * FROM clients WHERE (clientId = " + StaticValues.getClientId() + ")";
    PreparedStatement prepare = conn.prepareStatement(sql);
    ResultSet result = prepare.executeQuery();
    while (result.next()) {
        firstName.setText(result.getString("firstName"));
        secondName.setText(result.getString("secondName"));
        phone.setText(result.getString("phone"));
        other.setText(result.getString("other"));

    }
}catch (ClassNotFoundException | SQLException e) {
    e.printStackTrace();
}


    }
    private boolean clientAdd() throws SQLException, ClassNotFoundException {
        if ((firstName.getText().length() >= 3) && (secondName.getText().length() >= 3)) {
            Connection conn = DataBaseConnection.connectionOpen();

            String sql = "UPDATE clients SET firstName = ?, secondname = ?, phone = ?, other = ? WHERE clientId=?";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setString(1, firstName.getText());
            prepare.setString(2, secondName.getText());
            prepare.setString(3, phone.getText());
            prepare.setString(4, other.getText());
            prepare.setInt(5, StaticValues.getClientId());
            prepare.executeUpdate();
            return true;

        }else{
            label.setText("Введены некорректные дынные");
            return false;
        }

        }



    }

