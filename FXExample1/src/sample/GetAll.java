package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by AlekseiSamoilov on 04.12.15.
 */
public class GetAll {
    public ObservableList getAll() throws ClassNotFoundException, SQLException {
        Class.forName(MyConnection.drvStr);
        Connection conn = DriverManager.getConnection(MyConnection.connStr);
        String sql = "SELECT * FROM works";
        PreparedStatement statement = conn.prepareStatement(sql);

        ResultSet result = statement.executeQuery();

        ObservableList<String> listCash = FXCollections.observableArrayList();
        while(result.next()){
            String all;
            int id = result.getInt("id");
            System.out.println(id);
            String date = result.getString("date");
            System.out.println(date);
            float many = result.getFloat("price");
            String other = result.getString("other");
            String master = result.getString("master");
            String works = result.getString("works");
            String car = result.getString("car");
            String client = result.getString("client");
            String tel = result.getString("tel");
            String manager = result.getString("manager");

            all = id + " " + date + " " + manager + " " + many + " " + other + " " + master + " " + works + " " + car  + " " + client + " " + tel;
            listCash.add(all);
        }

        conn.close();
        return listCash;
    }
}
