package com.alekseysamoylov.carrepaircenter;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Aleksey Samoylov on 29.12.2015.
 */
public class WorksTableClass {


    public SimpleIntegerProperty workId = new SimpleIntegerProperty();
    public SimpleStringProperty carName = new SimpleStringProperty();
    public SimpleStringProperty workName = new SimpleStringProperty();
    public SimpleFloatProperty price = new SimpleFloatProperty();


    public int getWorkId() {
        return workId.get();
    }

    public String getCarName() {
        return carName.get();
    }


    public String getWorkName() {
        return workName.get();
    }


    public float getPrice() {
        return price.get();
    }

}
