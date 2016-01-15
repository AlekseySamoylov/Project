package sample;

/*
  Created by Aleksey Samoylov on 31/12/15.
 */
public class StaticValues {

    private static String login;
    private static String password;
    public static String name;
    private static int clientId;

    public static int getClientId() {return clientId;    }

    public static void setClientId(int clientUpdateId) {  StaticValues.clientId = clientUpdateId;  }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        StaticValues.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        StaticValues.password = password;
    }


}
