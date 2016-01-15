package com.alekseysamoylov.carrepaircenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/*
 * Created by Aleksey Samoylov on 29.12.2015.
 */
public class MainStageController implements Initializable {

    @FXML private Label label;
    @FXML private TextField filterField;
    @FXML private TextField moneyPut;
    @FXML private ComboBox<String> managerBox;
    private ObservableList<String> managerList = FXCollections.observableArrayList();

    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button updateButton;
    @FXML private Button printButton;
    @FXML private Button payButton;


    @FXML private TableView<OrdersTableClass> tableView;
    @FXML private TableColumn<OrdersTableClass, Integer> orderId;
    @FXML private TableColumn<OrdersTableClass, String> orderDate;
    @FXML private TableColumn<OrdersTableClass, String> client;
    @FXML private TableColumn<OrdersTableClass, String> manager;
    @FXML private TableColumn<OrdersTableClass, String> orderName;
    @FXML private TableColumn<OrdersTableClass, Float> summ;
    @FXML private TableColumn<OrdersTableClass, Float> purchase;

    private ObservableList<OrdersTableClass> data;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            refreshTable();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void clickSQL() throws IOException {
        new StageClose(label);
        new NewStage("sql.fxml");
    }

    @FXML
    private void clickClients() throws IOException {
        new StageClose(label);
        new NewStage("clients.fxml");
    }


    @FXML
    private void clickCars() throws IOException {
        new StageClose(label);
        new NewStage("cars.fxml");
    }

    @FXML
    private void clickWorks() throws IOException {
        new StageClose(label);
        new NewStage("works.fxml");
    }


    @FXML
    private void clickDelete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление Записи");
        alert.setHeaderText("Удаление " + tableView.getSelectionModel().getSelectedItem().getClient());
        alert.setContentText("Вы уверены, что хотите удалить данные?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                Connection connection = DataBaseConnection.connectionOpen();
                String sql = "DELETE FROM ORDERS WHERE ORDERID = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, tableView.getSelectionModel().getSelectedItem().getOrderId());
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
    private void clickAdd() {
        try{
        System.out.println(StaticValues.getOrderId());
        SimpleDateFormat date = new SimpleDateFormat("dd-MMM-yy");
        String ss = date.format(new Date());
        Connection connection = DataBaseConnection.connectionOpen();
        String sql = "INSERT INTO ORDERS (ORDERID, ORDERDATE) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, StaticValues.getOrderId());
        preparedStatement.setString(2, ss);
        preparedStatement.executeUpdate();
            StaticValues.setWillDelete(true);

        new StageClose(label);
        new NewStage("purchaseOrder.fxml");
        } catch (IOException| SQLException| ClassNotFoundException ex){
            label.setText("Ошибка");
            ex.printStackTrace();
        }
    }

    @FXML
    private void clickUpdate() {
   doubleClick();
    }


    @FXML
    private void addMoney() {
        try{
        Connection connection = DataBaseConnection.connectionOpen();
        String sql = "UPDATE ORDERS SET PURCHASE = ? WHERE ORDERID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setFloat(1, Float.parseFloat(moneyPut.getText()));
        preparedStatement.setInt(2, tableView.getSelectionModel().getSelectedItem().getOrderId());
        preparedStatement.executeUpdate();
        moneyPut.setText("");
        label.setText("Платеж проведен успешно");
        refreshTable();
        } catch (SQLException | ClassNotFoundException e) {
            label.setText("Wrong!!!");
            e.printStackTrace();

        }

    }
    @FXML
    private void clickManagerBox() {
        try{
            Connection connection = DataBaseConnection.connectionOpen();
            String sql = "SELECT managerid FROM managers WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, managerBox.getValue());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                StaticValues.setManagerId(resultSet.getInt(1));
            }
            StaticValues.setManager(managerBox.getValue());
            refreshTable();
        }catch ( SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
            label.setText("Wrong!!!");
        }

    }


    private void refreshTable() throws SQLException, ClassNotFoundException {



        StaticValues.setOrderId(0);
        StaticValues.setClientCarId(0);
        StaticValues.setWorkId(0);
        StaticValues.setMaster("Выберите мастера");
        StaticValues.setMasterId(0);
        StaticValues.setPrice(0);
        StaticValues.setWorkName("");
        StaticValues.setModel("");
        StaticValues.setMark("");
        managerList.clear();


        updateBox();
        managerBox.setValue(StaticValues.getManager());
        if(!managerBox.getValue().equals("Менеджер")){
            addButton.setDisable(false);
            updateButton.setDisable(false);
            deleteButton.setDisable(false);
            tableView.setDisable(false);
            printButton.setDisable(false);
            payButton.setDisable(false);
        }

        tableView.setRowFactory(rf ->{
            TableRow<OrdersTableClass> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    doubleClick();
                }
            });
            return row;
        });

        int ordId;
        data = FXCollections.observableArrayList();
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        client.setCellValueFactory(new PropertyValueFactory<>("client"));
        manager.setCellValueFactory(new PropertyValueFactory<>("manager"));
        orderName.setCellValueFactory(new PropertyValueFactory<>("orderName"));
        summ.setCellValueFactory(new PropertyValueFactory<>("summ"));
        purchase.setCellValueFactory(new PropertyValueFactory<>("purchase"));
        Connection connection = DataBaseConnection.connectionOpen();
        String sql0 = "SELECT ORDERID FROM ORDERS";
        PreparedStatement preparedStatement0 = connection.prepareStatement(sql0);
        ResultSet resultSet0 = preparedStatement0.executeQuery();
        while (resultSet0.next()){
            ordId = resultSet0.getInt(1);
            StaticValues.setOrderId(++ordId);
        }

        String sql =
                "SELECT ORDERS.ORDERID, TO_CHAR(ORDERS.ORDERDATE, 'dd.mm.yyyy'), CLIENTS.FIRSTNAME, " +
                    "CLIENTS.SECONDNAME, CARS.MARK, CARS.MODEL, CLIENTSCARS.CARNUMBER, " +
                    "MANAGERS.NAME, ORDERS.ORDERNAME, ORDERS.SUMM, ORDERS.PURCHASE " +
                "FROM ORDERS " +
                "JOIN CLIENTSCARS " +
                "ON ORDERS.CLIENTCARID = CLIENTSCARS.CLIENTCARID " +
                "JOIN MANAGERS " +
                "ON ORDERS.MANAGERID = MANAGERS.MANAGERID " +
                "JOIN CLIENTS " +
                "ON CLIENTSCARS.CLIENTID = CLIENTS.CLIENTID " +
                "JOIN CARS " +
                "ON CLIENTSCARS.CARID = CARS.CARID ORDER BY ORDERS.ORDERID";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            OrdersTableClass ordersTableClass = new OrdersTableClass();
            ordersTableClass.orderId.set(resultSet.getInt(1));
            ordersTableClass.orderDate.set(resultSet.getString(2));
            ordersTableClass.client.set(resultSet.getString(3) + " " + resultSet.getString(4) + " " + resultSet.getString(5) + " " + resultSet.getString(6) + " " + resultSet.getString(7));
            ordersTableClass.manager.set(resultSet.getString(8));
            ordersTableClass.orderName.set(resultSet.getString(9));
            ordersTableClass.summ.set(resultSet.getFloat(10));
            ordersTableClass.purchase.set(resultSet.getFloat(11));
            data.add(ordersTableClass);
            //Подготовка к созданию нового ордера инкрементом последнего ид ордеров

        }
        tableView.setItems(data);


        search();

    }
    private void updateBox() throws SQLException, ClassNotFoundException {
        Connection connection = DataBaseConnection.connectionOpen();
        String sql = "SELECT name FROM managers";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            managerList.add(resultSet.getString(1));
        }
        managerBox.setItems(managerList);



    }

    private void search(){
        FilteredList<OrdersTableClass> filteredData = new FilteredList<>(data, p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tableView -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (tableView.getOrderName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (tableView.getClient().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<OrdersTableClass> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }

    private void doubleClick() {
        try{
            StaticValues.setOrderId(tableView.getSelectionModel().getSelectedItem().getOrderId());
            Connection connection = DataBaseConnection.connectionOpen();
            String sql = "SELECT CLIENTCARID, MANAGERID FROM ORDERS WHERE ORDERID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tableView.getSelectionModel().getSelectedItem().getOrderId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                StaticValues.setClientCarId(resultSet.getInt(1));
                StaticValues.setManagerId(resultSet.getInt(2));
            }
            StaticValues.setWillDelete(false);
            new StageClose(label);
            new NewStage("purchaseOrder.fxml");
        } catch (IOException| SQLException| ClassNotFoundException ex){
            label.setText("Ошибка");
            ex.printStackTrace();

        }
    }

    @FXML
    private void printOrder(){
        String printerText = "******************************************************************************** \n"
                            +"                                                                                 \n"
                            +"                              АВТОСЕРВИС КАМЕНКА                                \n"
                            +"_________________________________________________________________________________\n"
                            + "********************************************************************************\n"
                            + "                                                          ИП Терещенко Н.М.   \n"
                            + "                                                          Адрес: г. Пермь, ул    \n"
                            + "                                                          2-я Новгородская, 108   \n"
                            + "                                                          тел. 2-34-32-33      \n"
                            + "                                                          ИНН 593100955931  \n"
                            + "                                                          ОГРН 593100955931    \n"
                            + "                                                          Банковский счет:  \n"
                            + "                                                          № 4080281014977000588 \n\n"
                            + "                             Акт выполненных работ №" + StaticValues.getOrderId() +"\n\n"+
                              " № Название операции             Цена | Кол-во | Сумма\n";
        Connection connection = null;
        try {
            connection = DataBaseConnection.connectionOpen();
            String sql =
                    "SELECT WORK.WORKNAME, MASTERS.NAME, " +
                            "OPERATIONS.PRICE, OPERATIONS.NUMBERWORK, OPERATIONS.SUMM " +
                            "FROM OPERATIONS " +
                            "JOIN WORK " +
                            "ON OPERATIONS.WORKID = WORK.WORKID " +
                            "JOIN MASTERS " +
                            "ON OPERATIONS.MASTERID = MASTERS.MASTERID " +
                            "WHERE ORDERID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tableView.getSelectionModel().getSelectedItem().getOrderId());
            ResultSet resultSet = preparedStatement.executeQuery();
            int count1 =1;
            while (resultSet.next()){


                printerText = printerText + " " + count1 + " " +
                        resultSet.getString(1) +"    " +resultSet.getFloat(3) + "р. " + resultSet.getInt(4) + "шт. " + resultSet.getFloat(5) + "р.\n";

                count1++;
            }

            printerText = printerText + "\n______________________________________________________________________\n\n" +
                                        "                                           Итого: " + tableView.getSelectionModel().getSelectedItem().getSumm() + "р.\n\n"
            + "Менеджер:                                 Клиент:\n"
            + tableView.getSelectionModel().getSelectedItem().getManager() + "                       ";
            String clientName = "";
            String sql2 = "SELECT CLIENTS.FIRSTNAME, CLIENTS.SECONDNAME " +
                    "FROM ORDERS " +
                    "JOIN CLIENTSCARS " +
                    "ON ORDERS.CLIENTCARID = CLIENTSCARS.CLIENTCARID " +
                    "JOIN CLIENTS " +
                    "ON CLIENTSCARS.CLIENTID = CLIENTS.CLIENTID " +
                    "WHERE ORDERID = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            preparedStatement2.setInt(1, tableView.getSelectionModel().getSelectedItem().getOrderId());
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (resultSet2.next()){
                clientName = resultSet2.getString(1) + " " + resultSet2.getString(2);
            }
            printerText = printerText + clientName + " \n ________________________                 __________________________ \n                  М.П. \n\n"
                    + tableView.getSelectionModel().getSelectedItem().getOrderDate() + "г.";

            StaticValues.setPrintOrder(printerText);
            new StageClose(label);
            new NewStage("printer.fxml");




        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
        }


    }
}
