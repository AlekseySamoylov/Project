package com.alekseysamoylov.connecttooracle;

/**
 * Created by AlekseiSamoilov on 21/12/15.
 */

import java.sql.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@83.219.1.4:1521:xe", "alekseysamoylov", "89028035276");
        Statement stat = con.createStatement();
//        String sql = "INSERT INTO clients (firstname, secondname, phone) VALUES ('Васили', 'Иванов', '899999999')";
//stat.executeUpdate(sql);
        String sql = "Select firstname from clients";
        ResultSet set = stat.executeQuery(sql);
        while (set.next()){
            System.out.println(set.getString("firstname"));
        }
con.close();

    }
}
