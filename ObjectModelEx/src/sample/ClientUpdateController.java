package sample;

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
            new NewScene("mainWindow.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickAddClient() throws ClassNotFoundException, SQLException {
        if ((firstName.getText().length() >= 3) && (secondName.getText().length() >= 3)) {
            Connection conn = null;
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@83.219.1.4:1521:xe", StaticValues.getLogin(), StaticValues.getPassword());
                String sql = "UPDATE clients SET firstName = ?, secondname = ?, phone = ?, other = ? WHERE clientId=?";
                PreparedStatement prepare = conn.prepareStatement(sql);
                prepare.setString(1, firstName.getText());
                prepare.setString(2, secondName.getText());
                prepare.setString(3, phone.getText());
                prepare.setString(4, other.getText());
                prepare.setInt(5, StaticValues.getClientId());
                prepare.executeUpdate();
            } catch (ClassNotFoundException | SQLException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            Stage stage = (Stage) phone.getScene().getWindow();
            stage.close();
            try {
                new NewScene("mainWindow.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            label.setText("Введены некорректные дынные");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
try {
    Connection conn = ConnectionClass.connectionClass();
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



    }

