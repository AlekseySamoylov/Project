package com.alekseysamlylov.magnitapp;

/*
 * Created by Aleksey Samoylov on 14.01.2016.
 */
public class StaticValues {

    private static String className;
    private static String connectionPath;
    private static String login;
    private static String password;

    public static String getClassName() {
        return className;
    }

    public static void setClassName(String className) {
        StaticValues.className = className;
    }

    public static String getConnectionPath() {
        return connectionPath;
    }

    public static void setConnectionPath(String connectionPath) {
        StaticValues.connectionPath = connectionPath;
    }

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
