package com.alekseysamoylov.carrepaircenter;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Aleksey Samoylov on 29.12.2015.
 */
public class OperationsTableClass {

    public SimpleIntegerProperty operationId = new SimpleIntegerProperty();
    public SimpleStringProperty operationName = new SimpleStringProperty();
    public SimpleStringProperty master = new SimpleStringProperty();
    public SimpleFloatProperty price = new SimpleFloatProperty();
    public SimpleIntegerProperty numberWorks = new SimpleIntegerProperty();
    public SimpleFloatProperty summ = new SimpleFloatProperty();

    public int getOperationId() {
        return operationId.get();
    }

    public String getOperationName() {
        return operationName.get();
    }

    public String getMaster() {
        return master.get();
    }


    public Float getPrice() {
        return price.get();
    }

    public Integer getNumberWorks() {
        return numberWorks.get();
    }

    public float getSumm() {
        return summ.get();
    }


}
