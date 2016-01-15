package sample;

import java.sql.*;

/*
  Created by Aleksey Samoylov on 31/12/15.
 */
public class LoginDB {

    public String loginDB(String login, String password) throws ClassNotFoundException, SQLException {
//        login = "alekseysamoylov";
//        password = "";
        Class.forName("oracle.jdbc.driver.OracleDriver");
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@83.219.1.4:1521:xe", login, password)) {
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
            return StaticValues.name;
        }

    }
}
