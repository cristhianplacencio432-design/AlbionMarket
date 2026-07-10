package com.mycompany.albionmarket;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        PrintWriter out = response.getWriter();

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        System.out.println("nombre: " + nombre);
System.out.println("email: " + email);
System.out.println("contrasena: " + contrasena);

        if (nombre == null || email == null || contrasena == null ||
            nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
            response.setStatus(400);
            out.print("{\"error\": \"Todos los campos son requeridos\"}");
            return;
        }

        try (Connection conn = ConexionDB.getConnection()) {

            // Verificar si el email ya existe
            String checkSQL = "SELECT id FROM usuarios WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSQL);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                response.setStatus(409);
                out.print("{\"error\": \"El correo ya esta registrado\"}");
                return;
            }

            // Insertar usuario
            String sql = "INSERT INTO usuarios (nombre, email, contrasena) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, email);
            stmt.setString(3, contrasena);
            stmt.executeUpdate();

            out.print("{\"success\": true, \"mensaje\": \"Usuario registrado correctamente\"}");

        } catch (Exception e) {
            response.setStatus(500);
            out.print("{\"error\": \"Error del servidor: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(200);
    }
}