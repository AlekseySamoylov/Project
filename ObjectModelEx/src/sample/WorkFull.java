package sample;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by AlekseiSamoilov on 14/12/15.
 */
public class WorkFull implements Serializable {
    //private String client;
    private Client client;
    private String manager;
    private HashMap<Integer, WorkSimple> worksMap = new HashMap<>();
    private String master;
    private float summa;
    private String other;
    private float procrntZaRabotu;
    ;

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public HashMap<Integer, WorkSimple> getWorksMap() {
        return worksMap;
    }

    public void setWorksInMap(WorkSimple workSimple) {
        WorkSimple work0 = new WorkSimple();
        worksMap.put(0, work0);
        this.worksMap.put(this.worksMap.size(), workSimple);
        this.summa += workSimple.getPrice();
        System.out.println(summa);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    //Сохранение клиента в БД клиентов. Вызывать вконце!!!
    //если новый
    public void saveClient(){
        ClientBase clientBase = new ClientBase();
        clientBase.setClientInMap(client);
    }
    //Если старый
    public void saveClient(int clientId){
        ClientBase clientBase = new ClientBase();

    }

}
