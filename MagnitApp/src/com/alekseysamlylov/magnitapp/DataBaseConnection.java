package com.alekseysamlylov.magnitapp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.sql.*;

/*
 * Created by Aleksey Samoylov on 14.01.2016.
 */
public class DataBaseConnection {

    private static final String CLASSNAME = "oracle.jdbc.driver.OracleDriver";
    private static final String CONNECTIONPATH = "jdbc:oracle:thin:@172.20.10.7:1521:xe";
    private static final String LOGIN = "alekseysamoylov";
    private static final String PASSWORD = "89028035276";
    private static String className;
    private static String connectionPath;
    private static String login;
    private static String password;



    public void setN(int n) {
        N = n;
    }

    private int N;

    public static String getClassName() {
        return className;
    }

    public static void setClassName(String className) {
        DataBaseConnection.className = className;
    }

    public static String getConnectionPath() {
        return connectionPath;
    }

    public static void setConnectionPath(String connectionPath) {
        DataBaseConnection.connectionPath = connectionPath;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        DataBaseConnection.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DataBaseConnection.password = password;
    }

    public int getN() {
        return N;
    }


    public void doThisExersizes1() throws ClassNotFoundException {

        Class.forName(className);
        try (Connection connection = DriverManager.getConnection(connectionPath, login, password)){

            String sql1 = "IF EXISTS (SELECT * FROM TEST) DELETE FROM TEST";
            Statement statement = connection.createStatement();
            statement.execute(sql1);
            String sql2 = "INSERT INTO TEST (COLUMN1) VALUES " + N;
            for (int i = 1; i>=N; i++) {
                statement.addBatch(sql2);
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void doThisExersizes2(int N) throws ClassNotFoundException {

        Class.forName(CLASSNAME);
        try (Connection connection = DriverManager.getConnection(CONNECTIONPATH, LOGIN, PASSWORD)){

            String sql1 = "DELETE FROM TEST";
            Statement statement = connection.createStatement();
            statement.execute(sql1);
            statement.close();
//            Statement statement1 = connection.createStatement();
//            String sql2 = "INSERT INTO TEST (COLUMN1) VALUES (";
//            for (int i = 1; i<=N; i++) {
//                statement1.addBatch(sql2 + i + ")");
//                System.out.println(i);
//            }
//            statement1.executeBatch();

            System.out.println("START");
           String sql3 = "BEGIN " +
                   "FOR v_Count IN 1..? LOOP " +
                   "INSERT INTO TEST(COLUMN1) " +
                   "VALUES (v_count); " +
                   "END LOOP; " +
                   "END;";
         CallableStatement callableStatement = connection.prepareCall(sql3);
            callableStatement.setInt(1, N);
            callableStatement.execute();
            System.out.println("OK");

            //Генерация xml очень много сторонних библиотек, но я написал самостоятельно

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("entries");
            document.appendChild(rootElement);

            Element childElement;

            Element fieldElement;

            String sql4 = "SELECT COLUMN1 FROM TEST ORDER BY COLUMN1";
            Statement statement4 = connection.createStatement();
            ResultSet resultSet4 = statement4.executeQuery(sql4);
            while (resultSet4.next()){
                childElement = document.createElement("entry");
                rootElement.appendChild(childElement);
                fieldElement = document.createElement("field");
                childElement.appendChild(fieldElement);
                fieldElement.appendChild(document.createTextNode(String.valueOf(resultSet4.getInt(1))));
                System.out.println(resultSet4.getInt(1));
            }
            //Coхранение первого xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
//            Разкоментировать, чтобы в красивом виде сохранять xml
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("1.xml"));
            transformer.transform(domSource, streamResult);

            //Чтение xml
            StreamSource streamSource = new StreamSource("style.xsl");

            StreamSource in = new StreamSource("1.xml");
            StreamResult out = new StreamResult(System.out);

            transformer.transform(in, out);
        } catch (SQLException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }

    }


    }
