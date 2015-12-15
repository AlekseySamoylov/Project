package sample;

import java.io.Serializable;

/**
 * Created by AlekseiSamoilov on 14/12/15.
 */
public class WorkSimple implements Serializable{
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String data;
    private String work;
    private float price;
    private String other;

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }


}
