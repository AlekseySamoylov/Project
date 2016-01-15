package com.alekseysamoylov.carrepaircenter;

/**
 * Created by Aleksey Samoylov on 29.12.2015.
 */
public class StaticValues {


    private static String manager = "Менеджер";

    public static String getMaster() {
        return master;
    }

    public static void setMaster(String master) {
        StaticValues.master = master;
    }

    public static String getManager() {
        return manager;
    }

    public static void setManager(String manager) {
        StaticValues.manager = manager;
    }

    public static Boolean getWillDelete() {
        return willDelete;
    }

    public static void setWillDelete(Boolean willDelete) {
        StaticValues.willDelete = willDelete;
    }

    public static String getModel() {
        return model;
    }

    public static void setModel(String model) {
        StaticValues.model = model;
    }

    public static String getMark() {
        return mark;
    }

    public static void setMark(String mark) {
        StaticValues.mark = mark;
    }

    private static String model = "";
    private static String mark = "";
    private static Boolean willDelete;
    private static String master = "Мастер";
    private static String login;
    private static String password;
    public static String name;
    private static int clientId;
    private static int clientCarId;

    public static int getManagerId() {
        return managerId;
    }

    public static void setManagerId(int managerId) {
        StaticValues.managerId = managerId;
    }

    public static int getMasterId() {
        return masterId;
    }

    public static void setMasterId(int masterId) {
        StaticValues.masterId = masterId;
    }

    public static int getWorkId() {
        return workId;
    }

    public static void setWorkId(int workId) {
        StaticValues.workId = workId;
    }

    public static String getWorkName() {
        return workName;
    }

    public static void setWorkName(String workName) {
        StaticValues.workName = workName;
    }

    public static float getSummaField() {
        return summaField;
    }

    public static void setSummaField(float summaField) {
        StaticValues.summaField = summaField;
    }

    public static String getPrintOrder() {
        return printOrder;
    }

    public static void setPrintOrder(String printOrder) {
        StaticValues.printOrder = printOrder;
    }

    public static String getDateOrder() {
        return dateOrder;
    }

    public static void setDateOrder(String dateOrder) {
        StaticValues.dateOrder = dateOrder;
    }

    private static String dateOrder;
    private static String printOrder;
    private static float summaField;
    private static int workId;
    private static int masterId;
    private static int managerId;
    // !!!!!!!!!!!!!!!!//
    private static int orderId;
    private static int operationId;
    //!!!!!!!!!!!!!!!!!//
    private static float price;
    private static String workName = "";




    public static int getClientCarId() {
        return clientCarId;
    }

    public static void setClientCarId(int clientCarId) {
        StaticValues.clientCarId = clientCarId;
    }

    public static int getOperationId() {
        return operationId;
    }

    public static void setOperationId(int operationId) {
        StaticValues.operationId = operationId;
    }

    public static float getPrice() {
        return price;
    }

    public static void setPrice(float price) {
        StaticValues.price = price;
    }

    public static int getOrderId() {
        return orderId;
    }

    public static void setOrderId(int orderId) {
        StaticValues.orderId = orderId;
    }


    public static int getClientId() {return clientId;    }

    public static void setClientId(int clientUpdateId) {  StaticValues.clientId = clientUpdateId;  }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        StaticValues.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        StaticValues.password = password;
    }

}
