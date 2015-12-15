package sample;

import java.io.*;

/**
 * Created by AlekseiSamoilov on 15/12/15.
 */
public class SaveAndLoadBases {
    public static final String  CLIENTBASE = "clientBase.ser";

    public void saveClientBase(ClientBase clientBase) throws IOException {
        ObjectOutputStream clientOutput = new ObjectOutputStream(new FileOutputStream(CLIENTBASE));
        clientOutput.writeObject(clientBase);
    }

    public ClientBase loadClientBase() throws IOException {
        ClientBase clientBase = new ClientBase();
        ObjectInputStream clientsInput = new ObjectInputStream(new FileInputStream(CLIENTBASE));
        try{
            clientBase = (ClientBase)clientsInput.readObject();
            return clientBase;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return clientBase;
        }finally{
            clientsInput.close();
        }

    }
}
