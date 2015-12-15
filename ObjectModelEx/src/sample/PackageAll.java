package sample;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by AlekseiSamoilov on 14/12/15.
 */
public class PackageAll implements Serializable{

    private HashMap<Integer,WorkFull> allWorksMap = new HashMap<>();

    public void saveAllTickets(WorkFull workTicket){

        WorkFull workFull0 = new WorkFull();
        allWorksMap.put(allWorksMap.size(), workTicket);

    }


}
