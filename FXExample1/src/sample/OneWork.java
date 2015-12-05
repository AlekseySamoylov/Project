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

//    private String manager;
//    private String master;
//    private String time;
//    private String client;
//    private String tel;
//    private String car;
//    private String works;
//    private float price;
//    private String other;

    public void setAll(String manager, String master, String time, String client, String tel, String car, String works, float price, String other) throws IOException, ClassNotFoundException {
//        this.manager = manager;
//        this.master = master;
//        this.time = time;
//        this.client = client;
//        this.tel = tel;
//        this.car = car;
//        this.works = works;
//        this.price = price;
//        this.other = other;

        Map<String, String> mapWorks = new TreeMap<String, String>();

if(new File("works.ser").exists()) {

    ObjectInputStream oistream = new ObjectInputStream(new FileInputStream("works.ser"));
    mapWorks = (Map<String, String>) oistream.readObject();

    String one = time + " " + client + " " + car + " " + tel;
    String two = manager + " " + works + " " + price + " " + other;
    mapWorks.put(one, two);
    System.out.println(mapWorks.containsValue(one));
    oistream.close();
    ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("works.ser"));
    outStream.writeObject(mapWorks);
    outStream.close();

}else{
    ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("works.ser"));
    outStream.writeObject(mapWorks.put("one", "one"));
    outStream.close();
}

    }



}
