package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by AlekseiSamoilov on 11/12/15.
 */
public class Configuration implements Serializable {

    private static final long serialVersionUID = 1l;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }


    private String id;
    private String password;
    private String check;

    public ArrayList<String> getManagers() {
        return managers;
    }

    public void setManagers(String manager) {
        this.managers.add(manager);
    }

    public ArrayList<String> getMasters() {
        return masters;
    }

    public void setMasters(String master) {
        this.masters.add(master);
    }

    public void clearArrays(){
        managers.clear();
        masters.clear();
    }

    private ArrayList<String> managers = new ArrayList<String>();
    private ArrayList<String> masters = new ArrayList<String>();


    @Override
    public String toString(){
        return new StringBuffer("id: ").append(this.id).append(" pass: ").append(this.password).toString();
    }


}
