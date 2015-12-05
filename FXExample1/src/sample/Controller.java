package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class Controller {

    ObservableList<String> list
            = FXCollections.observableArrayList("Женя ГЛМ","Антон ГЛМ","Саня Полазна","Женя Коми","Женя Сварщик","Саня Вышка");
    ObservableList<String> listMeneger
            = FXCollections.observableArrayList("Олег","Артур");

    @FXML
    private Label label;

    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField master;

    @FXML
    private TextField client;

    @FXML
    private TextField tel;

    @FXML
    private TextField car;

    @FXML
    private TextArea works;

    @FXML
    private TextField price;

    @FXML
    private TextArea other;

    @FXML
    private TableColumn dayT;

    @FXML
    private TableColumn masterT;

    @FXML
    private TableView<String> tableView;

    @FXML
    private TableColumn clientT;

    @FXML
    private ListView cashList;

    @FXML
    private TextArea printArea;

    @FXML
    private TextField searchField;

    @FXML
    private void searchAction(ActionEvent event) throws SQLException, ClassNotFoundException {
    SearchAll one = new SearchAll();

        ObservableList listAll = one.getAllSearch(searchField.getText());
        Collections.reverse(listAll);
        cashList.setItems(listAll);
    }

    @FXML
    private void refreshData(ActionEvent event){
        setAllEvents();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws ClassNotFoundException, SQLException {


        SimpleDateFormat date = new SimpleDateFormat("kk:mm dd/M/yyyy");
        String ss = date.format(new Date());
        String timeB = ss;
        String masterB = comboBox.getSelectionModel().getSelectedItem();
        String managerB = comboBox1.getSelectionModel().getSelectedItem();
        String clientB = client.getText().toUpperCase(Locale.FRENCH);
        String telB = tel.getText();
        String carB = car.getText();
        String worksB = works.getText();
        float priceB = 0;

        if((masterB == null || comboBox1.getSelectionModel().getSelectedItem() == null)||(masterB.equals("Выбери!"))){
            label.setText("viberi mastera");
        }else{
            try{
                priceB = Float.valueOf(price.getText().trim());
            }catch(NumberFormatException ex){
                price.setText("НЕВЕРНО! Введите просто цифру!");
            }
            String otherB = other.getText();

            OneWork one = new OneWork();
            try {
                one.setAll(managerB, masterB, timeB, clientB, telB, carB, worksB, priceB, otherB);
                label.setText("All");
            } catch (IOException e) {
                e.printStackTrace();
                label.setText("Wrong Serialize");
            }

            try{
                GetSerWorks two = new GetSerWorks();
                ObservableList listAll = two.getSerWorks();
                Collections.reverse(listAll);
                cashList.setItems(listAll);
                label.setText(label.getText() + " ok!");
            }catch(Exception ex){
                System.out.println(ex);
                label.setText("Wrong print table!");
            }
            comboBox.setItems(list);
            comboBox1.setItems(listMeneger);

            if (label.getText().equals("All ok!")){
                client.setText("");
                tel.setText("");
                car.setText("");
                works.setText("");
                price.setText("");
                other.setText("");


            }
            comboBox.getSelectionModel().select("Выбери!");



//
//            ConnSQL asss = new ConnSQL();
//            String asss2 = asss.connected(managerB, masterB, timeB, clientB, telB, carB, worksB, priceB, otherB);
//
//            label.setText(asss2);
//
//            if (asss2.equals("All right!")){
//                client.setText("");
//                tel.setText("");
//                car.setText("");
//                works.setText("");
//                price.setText("");
//                other.setText("");
//
//
//            }
//            comboBox.getSelectionModel().select("Выбери!");
//
//            setAllEvents();
  }
    }
    private void setAllEvents(){
        try{
            GetAll all = new GetAll();
            ObservableList listAll = all.getAll();
            Collections.reverse(listAll);
            cashList.setItems(listAll);
        }catch(Exception ex){
            System.out.println(ex);
        }
        comboBox.setItems(list);
        comboBox1.setItems(listMeneger);

    }
    @FXML
    private void clickPrint() throws ClassNotFoundException {
        String printText = cashList.getSelectionModel().getSelectedItems().toString();
        GetSerWorks three = new GetSerWorks();

        printArea.setText(three.getAllInfo(printText));
    }

//    private void serialWork() throws IOException {
//        SimpleDateFormat date = new SimpleDateFormat("kk:mm dd/M/yyyy");
//        String ss = date.format(new Date());
//        String timeB = ss;
//        String masterB = comboBox.getSelectionModel().getSelectedItem();
//        String managerB = comboBox1.getSelectionModel().getSelectedItem();
//        String clientB = client.getText().toUpperCase(Locale.FRENCH);
//        String telB = tel.getText();
//        String carB = car.getText();
//        String worksB = works.getText();
//        float priceB = 0;
//
//        if((masterB == null || comboBox1.getSelectionModel().getSelectedItem() == null)||(masterB.equals("Выбери!"))){
//            label.setText("viberi mastera");
//        }else{
//            try{
//                priceB = Float.valueOf(price.getText().trim());
//            }catch(NumberFormatException ex){
//                price.setText("НЕВЕРНО! Введите просто цифру!");
//            }
//            String otherB = other.getText();
//
//            OneWork asss = new OneWork();
//            asss.setAll(managerB, masterB, timeB, clientB, telB, carB, worksB, priceB, otherB);
//
//            ObjectOutputStream oStream = new ObjectOutputStream(new FileOutputStream("works.ser"));
//            oStream.writeObject(asss);
//            oStream.close();
//
//
//            label.setText("Serialize has completed");
//
//            if (label.getText().equals("Serialize has completed")){
//                client.setText("");
//                tel.setText("");
//                car.setText("");
//                works.setText("");
//                price.setText("");
//                other.setText("");
//
//
//            }
//            comboBox.getSelectionModel().select("Выбери!");
//
//            setAllEvents();
//        }
//    }



}
