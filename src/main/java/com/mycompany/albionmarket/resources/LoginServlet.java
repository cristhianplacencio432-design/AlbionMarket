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
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");

        if (email == null || contrasena == null || email.isEmpty() || contrasena.isEmpty()) {
            response.setStatus(400);
            out.print("{\"error\": \"Todos los campos son requeridos\"}");
            return;
        }

        try (Connection conn = ConexionDB.getConnection()) {

            String sql = "SELECT id, nombre, contrasena FROM usuarios WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String contrasenaDB = rs.getString("contrasena");
                if (contrasena.equals(contrasenaDB)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuarioId", rs.getInt("id"));
                    session.setAttribute("usuarioNombre", rs.getString("nombre"));
                    out.print("{\"success\": true, \"nombre\": \"" + rs.getString("nombre") + "\"}");
                } else {
                    response.setStatus(401);
                    out.print("{\"error\": \"Contrasena incorrecta\"}");
                }
            } else {
                response.setStatus(404);
                out.print("{\"error\": \"Usuario no encontrado\"}");
            }

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