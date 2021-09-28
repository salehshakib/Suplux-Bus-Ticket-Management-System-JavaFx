package net.codejava.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorDB{
    private String url = "jdbc:sqlserver://LAPTOP-49HF90UR\\SQLEXPRESS:1433;databaseName=Suplux-Dashboard";
    //private String url = "jdbc:sqlserver://DESKTOP-TC26BLK\SQLEXPRESS:1433;databaseName=Suplux-Dashboard";
    private String user = "sa";
    private String password = "p@ssword";
    private Connection connection;

    public ConnectorDB() throws SQLException {
        try{
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to Microsoft SQL server");

        }catch (SQLException e){
            System.out.println("oops you are having a bad day");
            e.printStackTrace();

        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
