package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/*
  Created by AlekseySamoylov on 31/12/15.
 */
public class ClientTableView {

    public SimpleStringProperty firstName = new SimpleStringProperty();
    public SimpleIntegerProperty clientId = new SimpleIntegerProperty();
    public SimpleStringProperty phone = new SimpleStringProperty();
    public SimpleStringProperty secondName = new SimpleStringProperty();

    public int getClientId() {
        return clientId.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getSecondName() {
        return secondName.get();
    }



}
