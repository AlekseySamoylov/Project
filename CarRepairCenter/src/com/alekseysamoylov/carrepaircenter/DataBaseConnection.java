package com.alekseysamoylov.carrepaircenter;

import java.sql.*;

/*
 * Created by Aleksey Samoylov on 31.12.2015.
 */

public class DataBaseConnection {
    private static final String CLASSNAME = "oracle.jdbc.driver.OracleDriver";
    private static String connectionPath = "jdbc:oracle:thin:@localhost:1521:xe";


    /*
        private static String connectionPath = "jdbc:oracle:thin:@172.20.10.7:1521:xe";

        "jdbc:oracle:thin:@172.20.10.7:1521:xe";
        */
    private static String settings;

    public String loginDB() throws ClassNotFoundException, SQLException {
        String login = "alekseysamoylov";
        String password = "89028035276";
        this.settings = connectionPath;
        Class.forName(CLASSNAME);
        return connectionTest(settings, login, password);
    }

    public String loginDB(String login, String password, String settings) throws ClassNotFoundException, SQLException {
        this.settings = settings;
        Class.forName(CLASSNAME);
        return connectionTest(settings, login, password);

    }

    public static Connection connectionOpen() throws ClassNotFoundException, SQLException {
        Class.forName(CLASSNAME);
        return DriverManager.getConnection(settings, StaticValues.getLogin(), StaticValues.getPassword());
    }


    public static void connectionClose() throws SQLException, ClassNotFoundException {
        Connection conn = connectionOpen();
        conn.close();
    }

    public String connectionTest(String settings, String login, String password) {
        try (Connection conn = DriverManager.getConnection(settings, login, password)) {
            String sql = "SELECT login FROM account";
            PreparedStatement prepare = conn.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                StaticValues.name = result.getString("login");
            }
            if (StaticValues.name != null) {
                StaticValues.setLogin(login);
                StaticValues.setPassword(password);
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return StaticValues.name;
    }
}
