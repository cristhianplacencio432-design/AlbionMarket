package com.mycompany.albionmarket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    public static Connection getConnection() throws SQLException {
        String host = System.getenv("RAILWAY_PRIVATE_DOMAIN");
        String database = System.getenv("MYSQL_DATABASE");
        String password = System.getenv("MYSQL_ROOT_PASSWORD");
        String user = "root";
        String port = "3306";

        if (host == null || database == null || password == null) {
            throw new SQLException("Variables de entorno de base de datos no configuradas");
        }

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database
                   + "?useSSL=false&allowPublicKeyRetrieval=true";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado: " + e.getMessage());
        }
    }
}