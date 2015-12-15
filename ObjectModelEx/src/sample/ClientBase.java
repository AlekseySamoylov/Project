package sample;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by AlekseiSamoilov on 14/12/15.
 */
public class ClientBase implements Serializable {
    private int clientId;
    private HashMap<Integer, Client> clientsMap = new HashMap<>();

    public HashMap<Integer, Client> getClientsMap() {
        return clientsMap;
    }



    public Client getClientFromMap(int clientId) {
        return clientsMap.get(clientId);
    }

    public void setClientInMap(Client client) {
        this.clientsMap.put((Integer)clientsMap.size(), client);
    }

    public void setClientInMap(int clientId, Client client) {
        this.clientsMap.put(clientId, client);
    }


}
