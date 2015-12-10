package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {

    ObservableList<String> list
            = FXCollections.observableArrayList("Стрелков Александр","Жигулев Александр","Закоптелов Антон","Вашкарин Евгений","Осипов Евгений","Плотников Евгений", "Автослесарь", "Автослесарь с золотыми руками");
    ObservableList<String> listMeneger
            = FXCollections.observableArrayList("Новиков Олег","Мезенцев Артур","Заместитель");

    @FXML
    private Label label;

    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private ComboBox<String> comboBox;

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
    private ListView cashList;

    @FXML
    private TextArea printArea;

    @FXML
    private TextField searchField;

    @FXML
    private ProgressBar progressSend;

    @FXML
    private void PrintCheck(ActionEvent event){
        String checkText = printArea.getText();
        Printer five = new Printer();
        five.printer(checkText);
        PrinterGo six = new PrinterGo();
        six.printPage();
        label.setText("Чек был отправлен на печать!");
    }

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
    private void refreshData(ActionEvent event){
        setAllEvents();
    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                new MailSendFile();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }).start();}

    //Сохранение данных о клиенте и работе
    @FXML
    private void handleButtonAction(ActionEvent event) throws ClassNotFoundException, SQLException {

                Date sec = new Date();
                SimpleDateFormat date = new SimpleDateFormat("kk:mm dd/M/yyyy");
                String ss = date.format(new Date());
                String timeB = sec.getTime() + "| "+ ss;
                String masterB = comboBox.getSelectionModel().getSelectedItem();
                String managerB = comboBox1.getSelectionModel().getSelectedItem();
                String clientB = client.getText().toUpperCase(Locale.FRENCH);
                String telB = tel.getText();
                String carB = car.getText();
                String worksB = works.getText();
                float priceB = 0;

                if((masterB == null || comboBox1.getSelectionModel().getSelectedItem() == null)||(masterB.equals("Выбери!"))){
                    label.setText("Не выбрано поле с именем Мастера или Менеджера");
                }else{
                    try{
                        priceB = Float.valueOf(price.getText().trim());
                        String otherB = other.getText();

                        try {
                            Task task = new Task<Void>() {
                                @Override public Void call() {
                                    final int max = 20000000;
                                    for (int i = 1; i <= max; i++) {
                                        updateProgress(i, max);
                                    }
                                    updateProgress(0,max);
                                    return null;
                                }
                            };

                            progressSend.progressProperty().bind(task.progressProperty());
                            final Thread progress = new Thread(task);
                            progress.start();
                            OneWork one = new OneWork();
                            one.setAll(managerB, masterB, timeB, clientB, telB, carB, worksB, priceB, otherB);
                            label.setText("All ok!");
                        } catch (IOException e) {
                            e.printStackTrace();
                            label.setText("Wrong Serialize");
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        setAllEvents();

                        if (label.getText().equals("ok!")){
                            client.setText("");
                            tel.setText("");
                            car.setText("");
                            works.setText("");
                            price.setText("");
                            price.setPromptText("99999");
                            other.setText("");
                        }
                        comboBox.getSelectionModel().select("Выбери!");

                    }catch(NumberFormatException ex){
                        price.clear();
                        price.setPromptText("НЕВЕРНО! Введите просто цифру!");
                        label.setText("Ошибка в поле \"Цена\"");
                    }
                }

    }
    //Установка всех данных в поля и таблицу
    private void setAllEvents(){
        try{
            GetSerWorks two = new GetSerWorks();
            ObservableList listAll = two.getSerWorks();
            Collections.reverse(listAll);
            cashList.setItems(listAll);
            label.setText("ok!");
        }catch(Exception ex){
            System.out.println(ex);
            label.setText("Wrong print table!");
        }
        comboBox.setItems(list);
        comboBox1.setItems(listMeneger);
    }

    //Просмотр информации из ячейки
    @FXML
    private void clickPrint() throws ClassNotFoundException {

        String printText = cashList.getSelectionModel().getSelectedItems().toString();

        System.out.println(printText);
        int longStr = printText.length();
        String keyFinal = printText.substring(1, (longStr-1));
        System.out.println(keyFinal);
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
    //Очистить поля ввода данных о клиенте
//    @FXML
//    private void clickClearAction(ActionEvent event){
//        client.setText("");
//                tel.setText("");
//                car.setText("");
//                works.setText("");
//                price.setText("");
//                other.setText("");
//        comboBox.getSelectionModel().select("Выбери!");
//    }


}
