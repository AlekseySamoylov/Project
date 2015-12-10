package sample;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by AlekseiSamoilov on 05.12.15.
 */
public class OneWork {
//Сохранение значений в файле
    public void setAll(String manager, String master, String time, String client, String tel, String car, String works, float price, String other) throws IOException, ClassNotFoundException {
        Map<String, String> mapWorks = new TreeMap<String, String>();
        int timeLenght = time.length();
        String shortTime = time.substring(timeLenght-11);
        String one = time + " " + client + " " + " | " + car + " | " + master + " | " + manager;
        String two = "\n\nКлиент: " + client +"\nНомер телефона клиента: " + tel + "\nАвтомобиль: " + car + "\nРемонтные работы производил мастер: " + master + "\nАвтомобиль принял/выдал менеджер: " + manager +  "\nПеречень выполненных работ, использованных запчастей и материалов: \n" + works + "\nЦена: \n" + String.valueOf(price) + "\nПримечания: \n" + other + "\nДата: " + shortTime;
        String three = time.substring(timeLenght-16) + " " + client + " " + " | " + car + " | " + master + " | " + manager;
        //если файл существует
if(new File("works.ser").exists()) {
    ObjectInputStream oistream = new ObjectInputStream(new FileInputStream("works.ser"));
    mapWorks = (Map<String, String>)oistream.readObject();
     mapWorks.put(one, two);
    System.out.println(mapWorks.containsValue(one));
    oistream.close();
    ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("works.ser"));
    outStream.writeObject(mapWorks);
    outStream.close();
    //если файл еще не создали
}else{
    ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("works.ser"));
    mapWorks.put(one, two);
    outStream.writeObject(mapWorks);
    outStream.close();
        }
        MailSend newmail = new MailSend();
        try {
            newmail.generateAndSendEmail(three, two);
        }catch (Exception ex){
            System.out.println("Mail Ex" + ex.hashCode());
        }
    }
}
