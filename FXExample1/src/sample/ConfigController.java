package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AlekseiSamoilov on 10/12/15.
 */
public class ConfigController {


    @FXML
    private TextField login;

    @FXML
    private TextField id;

    @FXML
    private TextField pass;

    @FXML
    private Label label;

    @FXML
    private TextField manager;

    @FXML
    private TextField master;

    @FXML
    private TextArea checkEdit;

    Configuration configuration = new Configuration();

    @FXML
    private void clickRecovery(){
        File file = new File("conf.ser");
        file.delete();

    }

    @FXML
    private void editCheck() throws IOException, ClassNotFoundException {
        if(checkEdit.getText().length()>20)
        {

            configuration = readObj();
            configuration.setCheck(checkEdit.getText());
            writeConf(configuration);
            label.setText("Текст Чека отредактирован!");
        }else{
            label.setText("Ошибка недостаточно символов Нажмите Обновить");
        }

       }

    @FXML
    public void refresh() throws IOException, ClassNotFoundException {


            configuration = readObj();
            checkEdit.setText(configuration.getCheck());

    }

    @FXML
    private void loginEdit(ActionEvent event) throws IOException {
        if(login.getText().equals("890280a")){

                configuration = readObj();
                configuration.setId(id.getText());
                writeConf(configuration);
                label.setText("lEdit Successful!");
        }else{
            id.setText("Куда лезешь?");
            pass.setText("Проваливай отсюда!");
        }
        login.setText("");

    }

    @FXML
    private void passEdit(ActionEvent event) throws IOException {
        if(login.getText().equals("890280a")){

            configuration = readObj();
            configuration.setPassword(pass.getText());
            writeConf(configuration);
            label.setText("pEdit Successful!");
        }else{
            id.setText("Куда лезешь?");
            pass.setText("Проваливай отсюда!");
        }
        login.setText("");


    }

    @FXML
    private void setManager() throws IOException, ClassNotFoundException {

        configuration = readObj();
        configuration.setManagers(manager.getText());
        writeConf(configuration);
        label.setText("Менеджер добавлен!");
        manager.setText("");
    }

    @FXML
    private void setMaster() throws IOException, ClassNotFoundException {

        configuration = readObj();
        configuration.setMasters(master.getText());
        writeConf(configuration);
        label.setText("Мастер добавлен!");
        master.setText("");
    }

    @FXML
    private void clickSave() throws IOException {

    }
    private Configuration readObj() throws IOException {
        ObjectInputStream inputConf = new ObjectInputStream(new FileInputStream("conf.ser"));
        try{
            return configuration = (Configuration)inputConf.readObject();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            inputConf.close();
        }
        return null;
    }
    private void writeConf(Configuration configuration) throws IOException {
        ObjectOutputStream outputCon = new ObjectOutputStream(new FileOutputStream("conf.ser"));
        try{
           outputCon.writeObject(configuration);
        }finally {
            outputCon.close();
        }
    }


}
