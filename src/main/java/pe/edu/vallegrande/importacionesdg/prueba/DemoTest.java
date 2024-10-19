package pe.edu.vallegrande.importacionesdg.prueba;

import pe.edu.vallegrande.importacionesdg.db.AccesoDB;

import java.sql.Connection;

public class DemoTest {
    public static void main(String[] args) {
        try (Connection connection = AccesoDB.getConnection()) {
            if (connection != null) {
                System.out.println("Conexión a la base de datos establecida correctamente.");
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (Exception e) {
            System.out.println("Error al intentar conectarse a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
