package com.mycompany.albionmarket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    public static Connection getConnection() throws SQLException {
        String url = System.getenv("mysql://root:JqtePRgmSavzBExtrrhclJmZhZyjDoBB@acela.proxy.rlwy.net:45740/railway");
        
        if (url == null) {
            throw new SQLException("Variable MYSQL_PUBLIC_URL no configurada");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado: " + e.getMessage());
        }
    }
}