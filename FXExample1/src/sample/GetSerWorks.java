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
    public ObservableList<String> getSerWorks() throws ClassNotFoundException {

        ObservableList<String> workList = FXCollections.observableArrayList();
        try {
            ObjectInputStream workStream = new ObjectInputStream(new FileInputStream("works.ser"));
            Map<String, String> mapWork = (TreeMap<String, String>)workStream.readObject();
            workStream.close();
            for(Map.Entry<String, String> entry : mapWork.entrySet()){
                workList.add(entry.getKey());
               // System.out.println(entry.getKey());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return workList;

    }
    public String getAllInfo(String key) throws ClassNotFoundException {
        String allInfo;
        try {
            System.out.println(key);
            ObjectInputStream workStream = new ObjectInputStream(new FileInputStream("works.ser"));
            Map<String, String> mapWork = (TreeMap<String, String>)workStream.readObject();
         workStream.close();
                allInfo = key + " \n" + mapWork.get(key);
               // System.out.println(mapWork.get(key));
            return allInfo;

        } catch (IOException e) {
            e.printStackTrace();
            return "wrong!!!";
        }


    }

}
