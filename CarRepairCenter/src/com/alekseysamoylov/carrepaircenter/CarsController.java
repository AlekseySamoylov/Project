package com.alekseysamoylov.carrepaircenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Aleksey on 07.01.2016.
 */
public class CarsController implements Initializable {
    @FXML
    private TableView<ClientCarsTableView> tableView;
    @FXML private TableColumn<ClientCarsTableView, Integer> clientCarId;
    @FXML private TableColumn<ClientCarsTableView, String> mark;
    @FXML private TableColumn<ClientCarsTableView, String> model;
    @FXML private TableColumn<ClientCarsTableView, String> number;
    @FXML private TextField numberManual;
    @FXML private Label label;
    @FXML private ComboBox<String> markBox;
    @FXML private ComboBox<String> modelBox;
    @FXML private TextField markManual;
    @FXML private TextField modelManual;

    private List<String> markList = FXCollections.observableArrayList();
    private List<String> modelList = FXCollections.observableArrayList();




    @FXML
    private void clickSaveCar() throws SQLException, ClassNotFoundException {
        if(markManual.getLength() >= 2 && modelManual.getLength() >=1 && (!modelBox.getValue().equals(modelManual.getText()))) {
            Connection conn = DataBaseConnection.connectionOpen();

            String sql = "INSERT INTO cars (mark, model) VALUES (?, ?)";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setString(1, markManual.getText().toUpperCase());
            prepare.setString(2, modelManual.getText().toUpperCase());
            prepare.executeUpdate();
            refresh();
            label.setText("Сохранено!");
        }
        else {
            label.setText("Wrong!!!");
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            refresh();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML void selectMark() throws SQLException, ClassNotFoundException {

        modelList.clear();
        String markSelected = markBox.getValue();
        Connection conn = DataBaseConnection.connectionOpen();
        String sqlChoiseBoxes = "SELECT model FROM cars WHERE mark = ? ORDER BY MODEL";
        PreparedStatement statement = conn.prepareStatement(sqlChoiseBoxes);
        statement.setString(1, markSelected);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            modelList.add(resultSet.getString(1));
        }
        modelBox.setItems((ObservableList<String>) modelList);
        markManual.setText(markBox.getValue());

    }

    @FXML
    private void textClick(){
        markBox.setValue("");
    }
    @FXML
    private void clickBackToMain() throws IOException {
        new StageClose(label);
        new NewStage("mainStage.fxml");
    }


    private void refresh() throws SQLException, ClassNotFoundException {

        markList.clear();
        modelList.clear();
        markBox.setValue("");
        modelBox.setValue("");
        markManual.setText("");
        modelManual.setText("");

        Connection conn = DataBaseConnection.connectionOpen();

        String sqlChoiseBoxes = "SELECT mark FROM cars GROUP BY MARK ORDER BY mark ";
        PreparedStatement statement = conn.prepareStatement(sqlChoiseBoxes);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            markList.add(resultSet.getString(1));
        }
        markBox.setItems((ObservableList<String>) markList);

    }





}
