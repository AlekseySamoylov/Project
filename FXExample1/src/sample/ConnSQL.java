package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by AlekseiSamoilov on 04.12.15.
 */
public class ConnSQL {
    public String connected(String manager, String master, String time, String client, String tel, String car, String works, float price, String other) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String title = "Wrong!";
        Connection conn = DriverManager.getConnection(MyConnection.connStr);


        try{
            String sql = "INSERT INTO works (manager, master, date, client, tel, car, works, price, other) VALUES (?,?,?,?,?,?,?,?,?);";
            //String sql2 = "INSERT INTO many(many) VALUES (?);";
//                String sql = "INSERT INTO works (time) VALUES (?)";

            PreparedStatement stat = conn.prepareStatement(sql);

            stat.setString(1,manager);
            stat.setString(2,master);
            stat.setString(3,time);
            stat.setString(4,client);
            stat.setString(5,tel);
            stat.setString(6,car);
            stat.setString(7,works);
            stat.setFloat(8,price);
            stat.setString(9,other);
           // PreparedStatement stat2 = conn.prepareStatement(sql2);
           // stat2.setFloat(1, price);
            //stat.setString(1,other);


            stat.executeUpdate();
          //  stat2.executeUpdate();
            // conn.commit();
//                ResultSet result = stat.executeQuery();
//
//                while(result.next()){
//                    String res = result.getString("master");
//                 System.out.println(res);
//
//                }
            title = "All right!";
            return title;
        }catch(Exception ex){
            System.out.println(ex);
            // conn.rollback();

        }finally{
            conn.close();
        }




        return title;

    }
}
