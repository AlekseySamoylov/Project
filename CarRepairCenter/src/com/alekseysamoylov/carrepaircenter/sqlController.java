package com.alekseysamoylov.carrepaircenter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Aleksey Samoylov on 29.12.2015.
 */
public class sqlController implements Initializable{

    @FXML private TextArea printerArea;
    @FXML private Label label;
    @FXML private TextField sqlStatement;

    @FXML
    private void run() throws SQLException, ClassNotFoundException {
        String result = "";
        Connection connection = DataBaseConnection.connectionOpen();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement.getText());
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            result = result + resultSet.getString(1) + "\n";
        }
        printerArea.setText(result);

    }

    @FXML
    private void clickBack() throws IOException {
        new StageClose(label);
        new NewStage("mainStage.fxml");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sqlStatement.setText("SELECT PHONE FROM CLIENTS");
    }
}
