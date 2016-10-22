package sample;


import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;



public class Controller {




    @FXML
    private Label label;


    @FXML
    private ListView cashList;

    @FXML
    private TextArea printArea;

    @FXML
    private TextField searchField;



    @FXML
    public void buttonPressed(KeyEvent e)
    {
        if(e.getCode().toString().equals("ENTER"))
        {
           searchIn();
        }
    }

    @FXML
    private void searchAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        searchIn();
    }

    @FXML
    private void refreshData(ActionEvent event) throws IOException, ClassNotFoundException {
        setAllEvents();
   }


    //Просмотр информации из ячейки
    @FXML
    private void clickPrint() throws ClassNotFoundException {
        String printText = cashList.getSelectionModel().getSelectedItems().toString();
        int longStr = printText.length();
        String keyFinal = printText.substring(1, (longStr-1));
        GetSerWorks three = new GetSerWorks();
        printArea.setText(three.getAllInfo(keyFinal));
    }

    private void searchIn(){
        try{
            String key = searchField.getText();
            SearchSinc four = new SearchSinc();

            ObservableList listAll = four.searchSinc(key);
            Collections.reverse(listAll);
            cashList.setItems(listAll);
            label.setText("ok!");
        }catch(Exception ex){
            System.out.println(ex);
            label.setText("Wrong print table!");
        }
    }

    //Установка всех данных в поля и таблицу
    private void setAllEvents() throws ClassNotFoundException, IOException {
            GetSerWorks two = new GetSerWorks();
            ObservableList listAll = two.getSerWorks();
            Collections.reverse(listAll);
            cashList.setItems(listAll);
            label.setText("ok!");


    }




}
