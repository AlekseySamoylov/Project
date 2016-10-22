package sample;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.*;
import java.sql.SQLException;
import java.util.Objects;

/*
  Created by Aleksey Samoylov on 31/12/15.
 */
public class Controller {

    @FXML
    private Label label;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    public void buttonPressed(KeyEvent e) throws IOException {
        if(e.getCode().toString().equals("ENTER"))
        {
            clickOnButton();
        }
    }

    @FXML
    private void clickOnButton() throws IOException {
    new Thread(() -> {
   LoginDB loginDB = new LoginDB();
        try {
            label.setText("Incorrect Login or Password");
            label.setText(loginDB.loginDB(login.getText(), password.getText()));

        } catch (ClassNotFoundException e) {
            label.setText("Incorrect Login or Password");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            label.setText("Incorrect Login or Password");
        }
    }).run();
        if (!Objects.equals(label.getText(), "Incorrect Login or Password")){
            Stage stage = (Stage) label.getScene().getWindow();

            new NewScene("mainWindow.fxml");
            stage.close();
        }





//        String work = "Remont kolesa";
//        int kolvoWorks = 2;
//        float priceOneWork = 200;
//        String date = "10.12.2015";
//        String clientName = "Vasia";
//        String car = "Lada";
//        String telClienta = "9808790";
//        String master = "Golden Hands";
//        float procentZaRabotu = 0.5F;


//
////оздание клиента
//        Client client = new Client();
//        client.setName(clientName);
//        client.setTelephone(telClienta);
//        client.setCars(car);
//
////Создание атомарного экземпляра работы и опред. кол-ва
//        WorkSimple workSimple = new WorkSimple();
//        workSimple.setData(date);
//        workSimple.setPrice(priceOneWork);
//        workSimple.setWork(work);
//
//        //Создание заказа - наряда
//        WorkFull workFull = new WorkFull();
//        workFull.setClient(client);
//        workFull.setMaster(master);
//
////отправка нужного количества атомарных работ
//// в список работ
//        for(int i = 1; i<=kolvoWorks; i++){
//            workFull.setWorksInMap(workSimple);
//        }
//
//        ObjectOutputStream saveZakazNaryad = new ObjectOutputStream(new FileOutputStream("zakaz.ser"));
//        try{
//            saveZakazNaryad.writeObject(workFull);
//            System.out.println("Save completed!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            saveZakazNaryad.close();
//        }

    }

//    @FXML
//    private void seconClickButton() throws IOException {
//        WorkFull workFull;
//        ObjectInputStream loadZakazNaryad = new ObjectInputStream(new FileInputStream("zakaz.ser"));
//        try{
//            workFull = (WorkFull)loadZakazNaryad.readObject();
//            Client client = workFull.getClient();
//            System.out.println(client.getCar(0));
//            System.out.println(client.getName());
//            HashMap<Integer,WorkSimple> worksMap = workFull.getWorksMap();
//
//            for(Map.Entry<Integer,WorkSimple> entry : worksMap.entrySet()){
//                System.out.println(entry.getValue().getPrice() + entry.getValue().getWork());
//            }
//
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }finally {
//            loadZakazNaryad.close();
//        }
//    }




}
