package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/*
  Created by AlekseySamoylov on 31/12/15.
 */
public class ClientCarsTableView {
    public SimpleStringProperty mark = new SimpleStringProperty();
    public SimpleIntegerProperty clientCarId = new SimpleIntegerProperty();
    public SimpleStringProperty model = new SimpleStringProperty();
    public SimpleStringProperty number = new SimpleStringProperty();

    public int getClientCarId() {
        return clientCarId.get();
    }

    public String getMark() {
        return mark.get();
    }

    public String getModel() {
        return model.get();
    }

    public String getNumber() {
        return number.get();
    }

}
