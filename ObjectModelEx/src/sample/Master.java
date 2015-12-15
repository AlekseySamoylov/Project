package sample;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by AlekseiSamoilov on 14/12/15.
 */
public class Master implements Serializable {
    private int masterId;
    private String name;
    private float procent;
    private HashMap<Integer, WorkFull> worksMaster;
    private float dayMany;



}
