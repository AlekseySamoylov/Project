package com.alekseysamoylov.carrepaircenter;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/*
 * Created by Aleksey Samoylov on 29.12.2015.
 */
public class PurchaseOrderController implements Initializable {

    private String mailNameWorks = "";

    @FXML
    private Label label;

    @FXML private TextField clientField;
    @FXML private TextField carField;
    @FXML private TextField priceManual;
    @FXML private TextArea workField;
    @FXML private TextField summaField;
    @FXML private Label nameWork;
    @FXML private Button saveOperationButton;
    @FXML private Button saveOrderButton;



    @FXML private TableView<OperationsTableClass> tableView;
    @FXML private TableColumn<OperationsTableClass, Integer> operationId;
    @FXML private TableColumn<OperationsTableClass, String> operationName;
    @FXML private TableColumn<OperationsTableClass, String> master;
    @FXML private TableColumn<OperationsTableClass, Float> price;
    @FXML private TableColumn<OperationsTableClass, Float> numberWorks;
    @FXML private TableColumn<OperationsTableClass, Float> summ;
    private ObservableList<OperationsTableClass> data;

    @FXML private ComboBox<Integer> value;
    private ObservableList<Integer> valuesList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,50,100);

    @FXML private ComboBox<String> masterBox;

    @FXML private ComboBox<String> operationsNames;
    private ObservableList<String> operationValues = FXCollections.observableArrayList("Диагностика","Слесарные работы","Сварочные работы","Автоэлектрика","Кузовные работы","Шиномонтаж","Комплексные работы","Прочее");

    private ObservableList<String> masterList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operationsNames.setValue("Слесарные работы");
        operationsNames.setItems(operationValues);
        value.setItems(valuesList);
        value.setValue(1);
        try {
            refreshTable();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        checkSaveButton();

    }

    @FXML
    private void clickDeleteOperation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление операции");
        alert.setHeaderText("Удаление операции");
        alert.setContentText("Вы уверены, что хотите удалить данные об операции?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                Connection connection = DataBaseConnection.connectionOpen();
                String sql = "DELETE FROM OPERATIONS WHERE OPERATIONID = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, tableView.getSelectionModel().getSelectedItem().getOperationId());
                preparedStatement.executeUpdate();
                refreshTable();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                label.setText("Wrong!!!");

            }
        } else {
            alert.close();
        }

    }

    @FXML
    private void clickNameWorkBox(){
        checkSaveButton();
    }

    @FXML
    private void clickAddClient() throws IOException {
        new StageClose(label);
        new NewStage("clientInOrder.fxml");

    }


    @FXML
    private void clickAddWork() throws IOException {
        new StageClose(label);
        new NewStage("worksList.fxml");
    }

    @FXML
    private void clickBack() {
        try{
        if(StaticValues.getWillDelete()) {
            Connection connection = DataBaseConnection.connectionOpen();
            String sql = "DELETE FROM ORDERS WHERE ORDERID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, StaticValues.getOrderId());
            preparedStatement.executeUpdate();
        }
        clearStatics();
        new StageClose(label);
        new NewStage("mainStage.fxml");
    }catch (SQLException | ClassNotFoundException | IOException ex){
            ex.printStackTrace();
            label.setText("Wrong!!!");    }
    }

    @FXML
    private void clickSaveOperation() {
        try{
        Connection connection = DataBaseConnection.connectionOpen();
        String sql = "INSERT INTO OPERATIONS (ORDERID, MASTERID, WORKID, PRICE, NUMBERWORK, SUMM) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println(StaticValues.getOrderId());
        preparedStatement.setInt(1, StaticValues.getOrderId());
        preparedStatement.setInt(2, StaticValues.getMasterId());
        preparedStatement.setInt(3, StaticValues.getWorkId());
        preparedStatement.setFloat(4, Float.parseFloat(priceManual.getText()));
        preparedStatement.setInt(5, value.getValue());
        preparedStatement.setFloat(6, (Float.parseFloat(priceManual.getText())*value.getValue()));
        preparedStatement.executeUpdate();
        refreshTable();
            checkSaveButton();
        }catch ( SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
            label.setText("Wrong!!!");    }

    }
    @FXML
    private void clickSaveOrder(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection connection = DataBaseConnection.connectionOpen();
                    String sql = "UPDATE ORDERS " +
                            "SET CLIENTCARID = ?, MANAGERID = ?, ORDERNAME = ?, SUMM = ? WHERE ORDERID = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, StaticValues.getClientCarId());
                    preparedStatement.setInt(2, StaticValues.getManagerId());
                    preparedStatement.setString(3, operationsNames.getValue());
                    preparedStatement.setFloat(4, Float.parseFloat(summaField.getText()));
                    preparedStatement.setInt(5, StaticValues.getOrderId());
                    preparedStatement.executeUpdate();
                    StaticValues.setMaster("Мастер");
                    new StageClose(label);
                    new NewStage("mainStage.fxml");
                }catch (SQLException | ClassNotFoundException | IOException ex){
                    ex.printStackTrace();
                    label.setText("Wrong!!!");        }
            }
        }).run();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new MailSendFile().sendMess(clientField.getText()+ "|" + carField.getText(), mailNameWorks + " \n Сумма: " + summaField.getText());

                } catch (MessagingException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @FXML
    private void clickMasterBox() {
        try {
            Connection connection = DataBaseConnection.connectionOpen();
            String sql = "SELECT masterId FROM masters WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, masterBox.getValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StaticValues.setMasterId(resultSet.getInt(1));
            }
            StaticValues.setMaster(masterBox.getValue());
            checkSaveButton();
        }catch ( SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
            label.setText("Wrong!!!");        }


    }



    private void refreshTable() throws SQLException, ClassNotFoundException {
        StaticValues.setSummaField(0);

        masterBox.setValue(StaticValues.getMaster());
        workField.setText(StaticValues.getWorkName());
        priceManual.setText(String.valueOf(StaticValues.getPrice()));
        data = FXCollections.observableArrayList();
        operationId.setCellValueFactory(new PropertyValueFactory<>("operationId"));
        operationName.setCellValueFactory(new PropertyValueFactory<>("operationName"));
        master.setCellValueFactory(new PropertyValueFactory<>("master"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        numberWorks.setCellValueFactory(new PropertyValueFactory<>("numberWorks"));
        summ.setCellValueFactory(new PropertyValueFactory<>("summ"));
        Connection connection = DataBaseConnection.connectionOpen();
        String sql =
                "SELECT OPERATIONS.OPERATIONID, WORK.WORKNAME, MASTERS.NAME," +
                " OPERATIONS.PRICE, OPERATIONS.NUMBERWORK, OPERATIONS.SUMM " +
                        "FROM OPERATIONS " +
                        "JOIN WORK " +
                        "ON OPERATIONS.WORKID = WORK.WORKID " +
                        "JOIN MASTERS " +
                        "ON OPERATIONS.MASTERID = MASTERS.MASTERID" +
                        " WHERE ORDERID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, StaticValues.getOrderId());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            OperationsTableClass operationsTableClass = new OperationsTableClass();
            operationsTableClass.operationId.set(resultSet.getInt(1));
            operationsTableClass.operationName.set(resultSet.getString(2));
            mailNameWorks = mailNameWorks + "\n" + resultSet.getString(2);
            operationsTableClass.master.set(resultSet.getString(3));
            operationsTableClass.price.set(resultSet.getFloat(4));
            operationsTableClass.numberWorks.set(resultSet.getInt(5));
            operationsTableClass.summ.set(resultSet.getFloat(6));
            data.add(operationsTableClass);
            StaticValues.setSummaField(StaticValues.getSummaField()+resultSet.getFloat(6));
        }
        summaField.setText(String.valueOf(StaticValues.getSummaField()));
        tableView.setItems(data);
        updateBoxes();
        clientCarFields();

    }

    private void updateBoxes() throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnection.connectionOpen();

        String sql2 = "SELECT name FROM masters";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
        ResultSet resultSet2 = preparedStatement2.executeQuery();
        while (resultSet2.next()){
            masterList.add(resultSet2.getString(1));
        }
        masterBox.setItems(masterList);

}
    private void clientCarFields() throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnection.connectionOpen();
        String sql = "SELECT clients.firstName, clients.secondName, cars.mark, cars.model FROM clientscars JOIN clients ON clientscars.clientid = clients.clientid JOIN cars ON clientsCars.carId = cars.carid WHERE clientCarId = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, StaticValues.getClientCarId());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            clientField.setText(resultSet.getString(1) + " " + resultSet.getString(2));
            carField.setText(resultSet.getString(3) + " " + resultSet.getString(4));
        }
    }
    private void checkSaveButton(){
        try {
            if (StaticValues.getOrderId() > 0 && StaticValues.getClientCarId() > 0 && StaticValues.getWorkId() > 0 && StaticValues.getMasterId() > 0 && Float.parseFloat(priceManual.getText()) >= 0) {
                saveOperationButton.setDisable(false);

            }
            if (!tableView.getItems().isEmpty()) {
                saveOrderButton.setDisable(false);
            }

        }catch (Exception ex){
            ex.printStackTrace();
            label.setText("Wrong!!!");
        }
    }
    private void clearStatics(){
        StaticValues.setOrderId(0);
        StaticValues.setClientCarId(0);
        StaticValues.setWorkId(0);
        StaticValues.setMaster("Выберите мастера");
        StaticValues.setMasterId(0);
        StaticValues.setPrice(0);
        StaticValues.setWorkName("");
        StaticValues.setModel(".");
        StaticValues.setMark(".");
    }


}
