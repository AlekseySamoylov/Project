package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by AlekseiSamoilov on 05.12.15.
 */
public class GetSerWorks {

    //Подготовка данных(ключей) для отправки в таблицу
    public ObservableList<String> getSerWorks() throws ClassNotFoundException {
        ObservableList<String> workList = FXCollections.observableArrayList();
        try {
            ObjectInputStream workStream = new ObjectInputStream(new FileInputStream("works.ser"));
            Map<String, String> mapWork = (TreeMap<String, String>)workStream.readObject();
            workStream.close();
            for(Map.Entry<String, String> entry : mapWork.entrySet()){
                workList.add(entry.getKey());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return workList;
    }
    //Показать информацию о выделенном элементе таблицы
    public String getAllInfo(String key) throws ClassNotFoundException {
        String allInfo;
        try {
            System.out.println(key);
            ObjectInputStream workStream = new ObjectInputStream(new FileInputStream("works.ser"));
            Map<String, String> mapWork = (TreeMap<String, String>)workStream.readObject();
         workStream.close();
                allInfo = mapWork.get(key);
            return "                 Данные о произведенной работе                " + allInfo;

        } catch (IOException e) {
            e.printStackTrace();
            return "wrong!!!";
        }


    }

}
