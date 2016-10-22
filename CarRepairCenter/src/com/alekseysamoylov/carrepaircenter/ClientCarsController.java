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

public class ClientCarsController implements Initializable {
    @FXML
    private TableView<ClientCarsTableView> tableView;
    @FXML
    private TableColumn<ClientCarsTableView, Integer> clientCarId;
    @FXML
    private TableColumn<ClientCarsTableView, String> mark;
    @FXML
    private TableColumn<ClientCarsTableView, String> model;
    @FXML
    private TableColumn<ClientCarsTableView, String> number;
    @FXML
    private TextField numberManual;
    @FXML
    private Label label;
    @FXML
    private ComboBox<String> markBox;
    @FXML
    private ComboBox<String> modelBox;
    @FXML
    private TextField markManual;
    @FXML
    private TextField modelManual;

    private List<String> markList = FXCollections.observableArrayList();
    private List<String> modelList = FXCollections.observableArrayList();


    @FXML
    private void clickAddCar() throws ClassNotFoundException, SQLException, IOException {
        StaticValues.setClientCarId(0);

        int carIdLocal = 0;
        if ((markBox.getValue().length() >= 2) && (modelBox.getValue().length() >= 1) && (numberManual.getText().length() >= 3) && (modelManual.getText().length() < 1)) {
            Connection conn = DataBaseConnection.connectionOpen();

            String findCarIdSql = "SELECT carId FROM cars WHERE mark = ? AND model = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(findCarIdSql);
            preparedStatement.setString(1, markBox.getValue());
            preparedStatement.setString(2, modelBox.getValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carIdLocal = resultSet.getInt(1);
            }

            String sql = "INSERT INTO clientscars (carId, clientId, carNumber) VALUES (?, ?, ?)";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setInt(1, carIdLocal);
            prepare.setInt(2, StaticValues.getClientId());
            prepare.setString(3, numberManual.getText());
            prepare.executeUpdate();

            String sqlCarId = "SELECT clientCarId FROM clientsCars";
            PreparedStatement preparedStatement2 = conn.prepareStatement(sqlCarId);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()) {
                StaticValues.setClientCarId(resultSet2.getInt(1));
            }
            StaticValues.setMark(markBox.getValue());
            StaticValues.setModel(modelBox.getValue());
            new StageClose(label);
            new NewStage("purchaseOrder.fxml");


        } else if ((markManual.getText().length() >= 2) && (modelManual.getText().length() >= 1) && (numberManual.getText().length() >= 3)) {
            Connection conn = DataBaseConnection.connectionOpen();

            String sql = "INSERT INTO cars (mark, model) VALUES (?, ?)";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setString(1, markManual.getText().toUpperCase());
            prepare.setString(2, modelManual.getText().toUpperCase());
            prepare.executeUpdate();


            String findCarIdSql = "SELECT carId FROM cars WHERE mark = ? AND model = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(findCarIdSql);
            preparedStatement.setString(1, markManual.getText().toUpperCase());
            preparedStatement.setString(2, modelManual.getText().toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carIdLocal = resultSet.getInt(1);
            }
            System.out.print(carIdLocal);

            String sqlFin = "INSERT INTO clientscars (carId, clientId, carNumber) VALUES (?, ?, ?)";
            PreparedStatement prepareFin = conn.prepareStatement(sqlFin);
            prepareFin.setInt(1, carIdLocal);
            prepareFin.setInt(2, StaticValues.getClientId());
            prepareFin.setString(3, numberManual.getText());
            prepareFin.executeUpdate();

            String sqlCarId = "SELECT clientCarId FROM clientsCars";
            PreparedStatement preparedStatement2 = conn.prepareStatement(sqlCarId);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()) {
                StaticValues.setClientCarId(resultSet2.getInt(1));
            }
            StaticValues.setMark(markManual.getText().toUpperCase());
            StaticValues.setModel(modelManual.getText().toUpperCase());
            new StageClose(label);
            new NewStage("purchaseOrder.fxml");

        } else if ((markBox.getValue().length() < 2) && (modelBox.getValue().length() < 2) && (numberManual.getText().length() < 1) && (markManual.getText().length() < 1) && (modelManual.getText().length() < 1)) {

            doubleClick();
        } else {
            label.setText("Wrong!!!");
        }

    }

    @FXML
    private void clickSaveCar() throws ClassNotFoundException, SQLException, IOException {
        StaticValues.setClientCarId(0);

        int carIdLocal = 0;
        if ((markBox.getValue().length() >= 2) && (modelBox.getValue().length() >= 1) && (numberManual.getText().length() >= 3) && (modelManual.getText().length() < 1)) {
            Connection conn = DataBaseConnection.connectionOpen();

            String findCarIdSql = "SELECT carId FROM cars WHERE mark = ? AND model = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(findCarIdSql);
            preparedStatement.setString(1, markBox.getValue());
            preparedStatement.setString(2, modelBox.getValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carIdLocal = resultSet.getInt(1);
            }

            String sql = "INSERT INTO clientscars (carId, clientId, carNumber) VALUES (?, ?, ?)";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setInt(1, carIdLocal);
            prepare.setInt(2, StaticValues.getClientId());
            prepare.setString(3, numberManual.getText());
            prepare.executeUpdate();

            String sqlCarId = "SELECT clientCarId FROM clientsCars";
            PreparedStatement preparedStatement2 = conn.prepareStatement(sqlCarId);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()) {
                StaticValues.setClientCarId(resultSet2.getInt(1));
            }
            StaticValues.setMark(markBox.getValue());
            StaticValues.setModel(modelBox.getValue());
            new StageClose(label);
            new NewStage("clients.fxml");


        } else if ((markManual.getText().length() >= 2) && (modelManual.getText().length() >= 1) && (numberManual.getText().length() >= 3)) {
            Connection conn = DataBaseConnection.connectionOpen();

            String sql = "INSERT INTO cars (mark, model) VALUES (?, ?)";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setString(1, markManual.getText().toUpperCase());
            prepare.setString(2, modelManual.getText().toUpperCase());
            prepare.executeUpdate();


            String findCarIdSql = "SELECT carId FROM cars WHERE mark = ? AND model = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(findCarIdSql);
            preparedStatement.setString(1, markManual.getText().toUpperCase());
            preparedStatement.setString(2, modelManual.getText().toUpperCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                carIdLocal = resultSet.getInt(1);
            }
            System.out.print(carIdLocal);

            String sqlFin = "INSERT INTO clientscars (carId, clientId, carNumber) VALUES (?, ?, ?)";
            PreparedStatement prepareFin = conn.prepareStatement(sqlFin);
            prepareFin.setInt(1, carIdLocal);
            prepareFin.setInt(2, StaticValues.getClientId());
            prepareFin.setString(3, numberManual.getText());
            prepareFin.executeUpdate();

            String sqlCarId = "SELECT clientCarId FROM clientsCars";
            PreparedStatement preparedStatement2 = conn.prepareStatement(sqlCarId);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()) {
                StaticValues.setClientCarId(resultSet2.getInt(1));
            }
            StaticValues.setMark(markManual.getText().toUpperCase());
            StaticValues.setModel(modelManual.getText().toUpperCase());
            new StageClose(label);
            new NewStage("clients.fxml");

        } else {
            label.setText("Wrong!!!");
        }

    }


    @FXML
    private void clickDelete() throws ClassNotFoundException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление автомобиля");
        alert.setHeaderText("Удаление автомобиля клиента");
        alert.setContentText("Вы уверены, что хотите удалить данные о автомобиле клиента?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int carDelId = tableView.getSelectionModel().getSelectedItem().getClientCarId();
            Connection conn = DataBaseConnection.connectionOpen();
            String sql = "DELETE FROM clientscars WHERE (clientCarId = ?)";
            PreparedStatement prepare = conn.prepareStatement(sql);
            prepare.setInt(1, carDelId);
            prepare.executeUpdate();
            refresh();

        } else {
            alert.close();
        }
    }


    @FXML
    private void clickBack() {
        Stage stage = (Stage) label.getScene().getWindow();
        stage.close();
        try {
            new NewStage("clientInOrder.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickBackToClients() {
        Stage stage = (Stage) label.getScene().getWindow();
        stage.close();
        try {
            new NewStage("clients.fxml");
        } catch (IOException e) {
            e.printStackTrace();
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

    @FXML
    void selectMark() throws SQLException, ClassNotFoundException {
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
    private void textClick() {
        modelBox.setValue("");
        markBox.setValue("");
    }

    private void refresh() throws SQLException, ClassNotFoundException {
        tableView.setRowFactory(tv -> {
            TableRow<ClientCarsTableView> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) && (!label.getText().equals("Сохранить автомобиль"))) {
                    ClientCarsTableView rowData = row.getItem();
                    try {
                        doubleClick();
                    } catch (IOException | SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });


        markList.clear();
        modelList.clear();
        markBox.setValue("");
        modelBox.setValue("");
        markManual.setText("");
        modelManual.setText("");

        clientCarId.setCellValueFactory(
                new PropertyValueFactory<>("clientCarId"));
        mark.setCellValueFactory(
                new PropertyValueFactory<>("mark"));
        model.setCellValueFactory(
                new PropertyValueFactory<>("model"));
        number.setCellValueFactory(
                new PropertyValueFactory<>("number"));

        ObservableList<ClientCarsTableView> data = FXCollections.observableArrayList();
        Connection conn = DataBaseConnection.connectionOpen();
        String sql = "SELECT clientcarid, mark, model, carnumber FROM CLIENTSCARS JOIN CARS ON CLIENTSCARS.CARID = CARS.CARID WHERE (CLIENTID = " + StaticValues.getClientId() + ")";
        PreparedStatement prepare = conn.prepareStatement(sql);
        ResultSet result = prepare.executeQuery();
        while (result.next()) {
            ClientCarsTableView clientCarsTableView = new ClientCarsTableView();
            clientCarsTableView.clientCarId.set(result.getInt(1));
            clientCarsTableView.mark.set(result.getString(2));
            clientCarsTableView.model.set(result.getString(3));
            clientCarsTableView.number.set(result.getString(4));
            data.add(clientCarsTableView);

        }
        tableView.setItems(data);

        String sqlChoiseBoxes = "SELECT mark FROM cars GROUP BY MARK ORDER BY mark ";
        PreparedStatement statement = conn.prepareStatement(sqlChoiseBoxes);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            markList.add(resultSet.getString(1));
        }
        markBox.setItems((ObservableList<String>) markList);

    }

    private void doubleClick() throws SQLException, ClassNotFoundException, IOException {
        StaticValues.setClientCarId(tableView.getSelectionModel().getSelectedItem().getClientCarId());
        Connection conn = DataBaseConnection.connectionOpen();
        String findCarIdSql = "SELECT cars.mark, cars.model FROM clientscars join cars on clientscars.carid = cars.carid  WHERE clientcarid = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(findCarIdSql);
        preparedStatement.setInt(1, tableView.getSelectionModel().getSelectedItem().getClientCarId());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            StaticValues.setMark(resultSet.getString(1));
            StaticValues.setModel(resultSet.getString(2));
        }

        new StageClose(label);
        new NewStage("purchaseOrder.fxml");
    }


}
