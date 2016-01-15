package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientCarsController implements Initializable {
    @FXML private TableView<ClientCarsTableView> tableView;
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

    @FXML private void clickAddCar() throws ClassNotFoundException, SQLException {

        int carIdLocal = 0;
        if ((markBox.getValue().length() >= 3) && ( modelBox.getValue().length() >= 3) && (numberManual.getText().length()>=3) && (markManual.getText().length() < 1) && (modelManual.getText().length() < 1)){
            Connection conn = ConnectionClass.connectionClass();
            try {
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
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }else if((markManual.getText().length() >= 3) && (modelManual.getText().length() >= 3) && (numberManual.getText().length()>=3)){
           Connection conn = ConnectionClass.connectionClass();
            try {
                String sql = "INSERT INTO cars (mark, model) VALUES (?, ?)";
                PreparedStatement prepare = conn.prepareStatement(sql);
                prepare.setString(1, markManual.getText());
                prepare.setString(2, modelManual.getText());
                prepare.executeUpdate();


                String findCarIdSql = "SELECT carId FROM cars WHERE mark = ? AND model = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(findCarIdSql);
                preparedStatement.setString(1,  markManual.getText());
                preparedStatement.setString(2, modelManual.getText());
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
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            label.setText("Wrong!!!");
        }
        refresh();
    }

    @FXML private void clickDelete() throws ClassNotFoundException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление автомобиля");
        alert.setHeaderText("Удаление автомобиля клиента");
        alert.setContentText("Вы уверены, что хотите удалить данные о автомобиле клиента?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int carDelId = tableView.getSelectionModel().getSelectedItem().getClientCarId();
            Connection conn = ConnectionClass.connectionClass();
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
            new NewScene("mainWindow.fxml");
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

    @FXML void selectMark() throws SQLException, ClassNotFoundException {
        modelList.clear();
        String markSelected = markBox.getValue();
            Connection conn = ConnectionClass.connectionClass();
                String sqlChoiseBoxes = "SELECT model FROM cars WHERE mark = ?";
                PreparedStatement statement = conn.prepareStatement(sqlChoiseBoxes);
                statement.setString(1, markSelected);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    modelList.add(resultSet.getString(1));
                }
                modelBox.setItems((ObservableList<String>) modelList);
    }
    private void refresh() throws SQLException, ClassNotFoundException {
        markList.clear();
        modelList.clear();
        markBox.setValue("?");
        modelBox.setValue("?");
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
        Connection conn = ConnectionClass.connectionClass();
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




}
