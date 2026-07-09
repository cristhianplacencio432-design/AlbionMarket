package com.mycompany.albionmarket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexionDB {
private static final String HOST = "${{RAILWAY_PRIVATE_DOMAIN}}";
private static final String PORT = "3306";
private static final String DATABASE = "${{MYSQL_DATABASE}}";
private static final String USER = "root";
private static final String PASSWORD = "${{MYSQL_ROOT_PASSWORD}}";
private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=false&allowPublicKeyRetrieval=true";
public static Connection getConnection() throws SQLException {
try {
Class.forName("com.mysql.cj.jdbc.Driver");
return DriverManager.getConnection(URL, USER, PASSWORD);
} catch (ClassNotFoundException e) {
throw new SQLException("Driver MySQL no encontrado: " + e.getMessage());
}
}
}
