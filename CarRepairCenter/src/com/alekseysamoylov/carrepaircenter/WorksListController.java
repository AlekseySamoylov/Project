package com.alekseysamoylov.carrepaircenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/*
 * Created by Aleksey Samoylov on 31.12.2015.
 */
public class WorksListController implements Initializable {

    @FXML private Label label;
    @FXML private TextField filterField;

    @FXML private TableView<WorksTableClass> tableView;
    @FXML private TableColumn<WorksTableClass, Integer> workId;
    @FXML private TableColumn<WorksTableClass, String> carName;
    @FXML private TableColumn<WorksTableClass, String> workName;
    @FXML private TableColumn<WorksTableClass, Float> price;
    private ObservableList<WorksTableClass> data;

    @FXML private ComboBox <String> mark;
    @FXML private ComboBox <String> model;
    private List<String> markList = FXCollections.observableArrayList();
    private List<String> modelList = FXCollections.observableArrayList();

    @FXML private TextField markManual;
    @FXML private TextField modelManual;
    @FXML private TextArea workNameManual;
    @FXML private TextField priceManual;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setRowFactory( tv -> {
            TableRow<WorksTableClass> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        doubleClick();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
        mark.setValue(StaticValues.getMark());
        model.setValue(StaticValues.getModel());
        if (StaticValues.getMark().equals("ВАЗ")){
            filterField.setText("ВАЗ");
        }
        try {
            refreshTable();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    @FXML
    private void doubleClick() throws IOException {
        if(!label.getText().equals("Список операций")) {
            StaticValues.setWorkName(tableView.getSelectionModel().getSelectedItem().getWorkName());
            StaticValues.setPrice(tableView.getSelectionModel().getSelectedItem().getPrice());
            StaticValues.setWorkId(tableView.getSelectionModel().getSelectedItem().getWorkId());
            new StageClose(label);
            new NewStage("purchaseOrder.fxml");
        }

    }

    @FXML
    private void clickSaveWork() throws SQLException, ClassNotFoundException {
            int carIdLocal = 0;
            if ((mark.getValue().length() >= 2) && (model.getValue().length() >= 2) && (workNameManual.getText().length() >= 3) && (modelManual.getText().length() < 1) && (priceManual.getText().length() >= 0)) {
                Connection connection = DataBaseConnection.connectionOpen();

                String findCarIdSql = "SELECT carId FROM cars WHERE mark = ? AND model = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(findCarIdSql);
                preparedStatement.setString(1, mark.getValue());
                preparedStatement.setString(2, model.getValue());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    carIdLocal = resultSet.getInt(1);
                }

                String sql = "INSERT INTO work (carId, workName, price) VALUES (?, ?, ?)";
                PreparedStatement prepare = connection.prepareStatement(sql);
                prepare.setInt(1, carIdLocal);
                prepare.setString(2, workNameManual.getText());
                prepare.setFloat(3, Float.parseFloat(priceManual.getText()));
                prepare.executeUpdate();
                StaticValues.setWorkName(workNameManual.getText());
                StaticValues.setPrice(Float.parseFloat(priceManual.getText()));

refreshTable();
            } else if ((markManual.getText().length() >= 2) && (modelManual.getText().length() >= 1) && (workNameManual.getText().length() >= 3) && (priceManual.getText().length() >= 0)) {
                Connection connection = DataBaseConnection.connectionOpen();
                String sql = "INSERT INTO cars (mark, model) VALUES (?, ?)";
                PreparedStatement prepare = connection.prepareStatement(sql);
                prepare.setString(1, markManual.getText().toUpperCase().toUpperCase());
                prepare.setString(2, modelManual.getText().toUpperCase().toUpperCase());
                prepare.executeUpdate();


                String findCarIdSql = "SELECT carId FROM cars WHERE mark = ? AND model = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(findCarIdSql);
                preparedStatement.setString(1, markManual.getText().toUpperCase().toUpperCase());
                preparedStatement.setString(2, modelManual.getText().toUpperCase().toUpperCase());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    carIdLocal = resultSet.getInt(1);
                }
                System.out.print(carIdLocal);

                String sql2 = "INSERT INTO work (carId, workName, price) VALUES (?, ?, ?)";
                PreparedStatement prepare2 = connection.prepareStatement(sql2);
                prepare2.setInt(1, carIdLocal);
                prepare2.setString(2, workNameManual.getText());
                prepare2.setFloat(3, Float.parseFloat(priceManual.getText()));
                prepare2.executeUpdate();
refreshTable();
            }

    }

    @FXML private void clickAddWork() throws ClassNotFoundException, SQLException, IOException {

        try {
            int carIdLocal = 0;
            if ((mark.getValue().length() >= 2) && (model.getValue().length() >= 2) && (workNameManual.getText().length() >= 3) && (modelManual.getText().length() < 1) && (priceManual.getText().length() >= 0)) {
                Connection connection = DataBaseConnection.connectionOpen();

                String findCarIdSql = "SELECT carId FROM cars WHERE mark = ? AND model = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(findCarIdSql);
                preparedStatement.setString(1, mark.getValue());
                preparedStatement.setString(2, model.getValue());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    carIdLocal = resultSet.getInt(1);
                }

                String sql = "INSERT INTO work (carId, workName, price) VALUES (?, ?, ?)";
                PreparedStatement prepare = connection.prepareStatement(sql);
                prepare.setInt(1, carIdLocal);
                prepare.setString(2, workNameManual.getText());
                prepare.setFloat(3, Float.parseFloat(priceManual.getText()));
                prepare.executeUpdate();
                StaticValues.setWorkName(workNameManual.getText());
                StaticValues.setPrice(Float.parseFloat(priceManual.getText()));

                String sqlGetWorkId = "SELECT workId FROM work WHERE carId = ? AND workName = ?";
                PreparedStatement getWorksStatement = connection.prepareStatement(sqlGetWorkId);
                getWorksStatement.setInt(1, carIdLocal);
                getWorksStatement.setString(2, workNameManual.getText());
                ResultSet getWorksResult = getWorksStatement.executeQuery();
                while (getWorksResult.next()) {
                    StaticValues.setWorkId(getWorksResult.getInt(1));
                }
                new StageClose(label);
                new NewStage("purchaseOrder.fxml");

            } else if ((markManual.getText().length() >= 2) && (modelManual.getText().length() >= 1) && (workNameManual.getText().length() >= 3) && (priceManual.getText().length() >= 0)) {
               Connection connection = DataBaseConnection.connectionOpen();
                    String sql = "INSERT INTO cars (mark, model) VALUES (?, ?)";
                    PreparedStatement prepare = connection.prepareStatement(sql);
                    prepare.setString(1, markManual.getText().toUpperCase().toUpperCase());
                    prepare.setString(2, modelManual.getText().toUpperCase().toUpperCase());
                    prepare.executeUpdate();


                    String findCarIdSql = "SELECT carId FROM cars WHERE mark = ? AND model = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(findCarIdSql);
                    preparedStatement.setString(1, markManual.getText().toUpperCase().toUpperCase());
                    preparedStatement.setString(2, modelManual.getText().toUpperCase().toUpperCase());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        carIdLocal = resultSet.getInt(1);
                    }
                    System.out.print(carIdLocal);

                    String sql2 = "INSERT INTO work (carId, workName, price) VALUES (?, ?, ?)";
                    PreparedStatement prepare2 = connection.prepareStatement(sql2);
                    prepare2.setInt(1, carIdLocal);
                    prepare2.setString(2, workNameManual.getText());
                    prepare2.setFloat(3, Float.parseFloat(priceManual.getText()));
                    prepare2.executeUpdate();

                StaticValues.setWorkName(workNameManual.getText());
                StaticValues.setPrice(Float.parseFloat(priceManual.getText()));

                    String sqlGetWorkId = "SELECT workId, price FROM work";
                    PreparedStatement getWorksStatement = connection.prepareStatement(sqlGetWorkId);
                    ResultSet getWorksResult = getWorksStatement.executeQuery();
                    while (getWorksResult.next()) {
                        StaticValues.setWorkId(getWorksResult.getInt(1));
                        StaticValues.setPrice(getWorksResult.getFloat(2));
                    }
                new StageClose(label);
                new NewStage("purchaseOrder.fxml");

            } else  if ((workNameManual.getText().length() < 2) && (markManual.getText().length() < 1) && (modelManual.getText().length() < 1) && (priceManual.getText().length() < 1)) {
                doubleClick();
            } else { label.setText("Wrong!!!"); }
        } catch (NumberFormatException ex) {
            label.setText("Введены некорректные данные в поле цена");
        }
    }

    @FXML void selectMark() throws SQLException, ClassNotFoundException {
        modelList.clear();
        String markSelected = mark.getValue();
        Connection connection = DataBaseConnection.connectionOpen();
        String sqlChoiseBoxes = "SELECT model FROM cars WHERE mark = ?";
        PreparedStatement statement = connection.prepareStatement(sqlChoiseBoxes);
        statement.setString(1, markSelected);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            modelList.add(resultSet.getString(1));
        }
        model.setItems((ObservableList<String>) modelList);
        markManual.setText(mark.getValue());
    }


    @FXML
    private void clickBackToMain() throws IOException {
        new StageClose(label);
        new NewStage("mainStage.fxml");
    }

    @FXML
    private void clickOnBack() throws IOException {
        new StageClose(label);
        new NewStage("purchaseOrder.fxml");
    }

    @FXML
    private void textClick(){
        model.setValue("");
        mark.setValue("");
    }

    @FXML
    private void clickDelete() throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnection.connectionOpen();
        String sql = "DELETE FROM WORK WHERE WORKID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, tableView.getSelectionModel().getSelectedItem().getWorkId());
        preparedStatement.executeUpdate();
        refreshTable();
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        markList.clear();
        modelList.clear();
        mark.setValue(StaticValues.getMark());
        model.setValue(StaticValues.getModel());
        markManual.setText("");
        modelManual.setText("");

        data = FXCollections.observableArrayList();
        workId.setCellValueFactory(new PropertyValueFactory<>("workId"));
        carName.setCellValueFactory(new PropertyValueFactory<>("carName"));
        workName.setCellValueFactory(new PropertyValueFactory<>("workName"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        Connection connection = DataBaseConnection.connectionOpen();
        String sql = "SELECT work.workId, cars.mark, cars.model, work.workName, work.price FROM work JOIN cars ON work.carId = cars.carId ORDER BY workId DESC";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            WorksTableClass worksTableClass = new WorksTableClass();
            worksTableClass.workId.set(resultSet.getInt(1));
            worksTableClass.carName.set(resultSet.getString(2) + " " + resultSet.getString(3));
            worksTableClass.workName.set(resultSet.getString(4));
            worksTableClass.price.set(resultSet.getFloat(5));
            data.add(worksTableClass);
        }
        tableView.setItems(data);

        String sqlChoiseBoxes = "SELECT mark FROM cars GROUP BY MARK ORDER BY mark ";
        PreparedStatement statement = connection.prepareStatement(sqlChoiseBoxes);
        ResultSet resultSet2 = statement.executeQuery();
        while (resultSet2.next()) {
            markList.add(resultSet2.getString(1));
        }
        mark.setItems((ObservableList<String>) markList);
        search();
    }

    private void search(){
        FilteredList<WorksTableClass> filteredData = new FilteredList<>(data, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tableView -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (tableView.getCarName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (tableView.getWorkName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<WorksTableClass> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableView.setItems(sortedData);
    }
    private void doubleClickTable(){
        try {
            StaticValues.setWorkId(tableView.getSelectionModel().getSelectedItem().getWorkId());
            StaticValues.setPrice(tableView.getSelectionModel().getSelectedItem().getPrice());
            StaticValues.setWorkName(tableView.getSelectionModel().getSelectedItem().getWorkName());


            new StageClose(label);
            new NewStage("purchaseOrder.fxml");

        }catch (RuntimeException | IOException ex){
            label.setText("Не выбрана операция");
        }
    }

}
