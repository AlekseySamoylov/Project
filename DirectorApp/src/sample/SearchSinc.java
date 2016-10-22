package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by alekseysamoylov on 12/5/15.
 */
public class SearchSinc {
    //Поиск данных в таблице
    public ObservableList<String> searchSinc(String key) throws ClassNotFoundException {
        String allInfo;
       ObservableList<String> result = FXCollections.observableArrayList();
        try {
            System.out.println(key);
            ObjectInputStream workStream = new ObjectInputStream(new FileInputStream("works.ser"));
            Map<String, String> mapWork = (TreeMap<String, String>)workStream.readObject();
            workStream.close();
            for(Map.Entry<String, String> entry : mapWork.entrySet()){
                if(entry.getKey().contains(key)){
                    result.add(entry.getKey());
                }
            }
return result;
        }catch (IOException e){
            e.printStackTrace();
            result.add("Wrong!!");
            return result;
        }
    }
}
