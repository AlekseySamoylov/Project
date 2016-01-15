package com.alekseysamoylov.carrepaircenter;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Aleksey Samoylov on 29.12.2015.
 */
public class OrdersTableClass {

    public SimpleIntegerProperty orderId = new SimpleIntegerProperty();
    public SimpleStringProperty orderDate = new SimpleStringProperty();
    public SimpleStringProperty client = new SimpleStringProperty();
    public SimpleStringProperty manager = new SimpleStringProperty();
    public SimpleStringProperty orderName = new SimpleStringProperty();
    public SimpleFloatProperty summ = new SimpleFloatProperty();
    public SimpleFloatProperty purchase = new SimpleFloatProperty();

    public int getOrderId() {
        return orderId.get();
    }

    public String getOrderDate() {
        return orderDate.get();
    }

    public String getClient() {
        return client.get();
    }


    public String getManager() {
        return manager.get();
    }

    public String getOrderName() {
        return orderName.get();
    }

    public float getSumm() {
        return summ.get();
    }


    public float getPurchase() {
        return purchase.get();
    }




}
