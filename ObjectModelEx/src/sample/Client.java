package sample;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by AlekseiSamoilov on 14/12/15.
 */
public class Client implements Serializable{
    private String name = null;
    private String telephone = null;
    private String skidka = null;
    private String other = null;
    private HashMap<Integer, String> cars = new HashMap<Integer, String>();

    private int hisWorks;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSkidka() {
        return skidka;
    }

    public void setSkidka(String skidka) {
        this.skidka = skidka;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getCar(int num) {
        return cars.get(num);
    }

    public void setCars(String car) {
        cars.put(0, "");
        System.out.println(cars.size());
        this.cars.put(cars.size(), car);
    }
    //Удаление тачки
    public void deleteCar(int carId){
        cars.remove(carId);
    }
    public HashMap<Integer, String> getCars() {
        return cars;
    }






}
