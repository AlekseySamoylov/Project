package sample;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by AlekseiSamoilov on 05.12.15.
 */
public class OneWork{
//Сохранение значений в файле
    public void setAll(String manager, String master, String time, String client, String tel, String car, String works, float price, String other) throws IOException, ClassNotFoundException {
        Map<String, String> mapWorks = new TreeMap<String, String>();
        //если файл существует
if(new File("works.ser").exists()) {
    ObjectInputStream oistream = new ObjectInputStream(new FileInputStream("works.ser"));
    mapWorks = (Map<String, String>)oistream.readObject();
    String one = time + " " + client + " " + " | " + car + " | " + master + " | " + manager;
    String two = "\n\nКлиент: " + client + "\nАвтомобиль: " + car + "\nМастер: " + master + "\nМенеджер: " + manager + "\nНомер телефона: " +tel + "\nПроизведенные работы : \n" + works + "\nЦена: \n" + String.valueOf(price) + "\nЗамечания: \n " + other;
    mapWorks.put(one, two);
    System.out.println(mapWorks.containsValue(one));
    oistream.close();
    ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("works.ser"));
    outStream.writeObject(mapWorks);
    outStream.close();
    //если файл еще не создали
}else{
    String one = time + " " + client + " " + " | " + car + " | " + master + " | " + manager;
    String two = "\n\nКлиент: " + client + "\nАвтомобиль: " + car + "\nМастер: " + master + "\nМенеджер: " + manager + "\nНомер телефона: " +tel + "\nПроизведенные работы : \n" + works + "\nЦена: \n" + String.valueOf(price) + "\nЗамечания: \n " + other;
    ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("works.ser"));
    mapWorks.put(one, two);
    outStream.writeObject(mapWorks);
    outStream.close();
        }
    }
}
