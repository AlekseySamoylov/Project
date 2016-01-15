package sample;

import java.sql.*;

/*
 Created by Aleksey Samoylov on 29.12.2015.
 */
public class ConnectionClass {
    public static Connection connectionClass() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection("jdbc:oracle:thin:@83.219.1.4:1521:xe", StaticValues.getLogin(), StaticValues.getPassword());
    }


    public static void connectionClose() throws SQLException, ClassNotFoundException {
        Connection conn = connectionClass();
        conn.close();
    }
}
