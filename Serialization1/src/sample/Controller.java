package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.*;
import java.util.ArrayList;

public class Controller {

    @FXML
    private Label label;

    @FXML
    private void button1(ActionEvent e) throws IOException {
        ArrayList<String> lina = new ArrayList<>();
        lina.add("Ok");
        lina.add("Ok");
        lina.add("Ok");
        ObservableList<String> onne = FXCollections.observableArrayList(lina);

//
//        Configuration one = new Configuration();
//        one.setId(one.managers.get(0));
//        one.setPassword(one.managers.get(0));

label.setText(onne.get(1));
//        ObjectOutputStream two = new ObjectOutputStream(new FileOutputStream("ass.ser"));
//        try{
//          two.writeObject(one);
//        }finally {
//            two.close();
//        }

    }

    @FXML
    private void button2(ActionEvent e) throws IOException {
        Configuration one = null;
        ObjectInputStream two = new ObjectInputStream(new FileInputStream("ass.ser"));
        try{
one = (Configuration)two.readObject();
            System.out.println(one.managers.get(0));
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }finally {
            two.close();
        }
        label.setText(one.managers.get(0));
    }


}
