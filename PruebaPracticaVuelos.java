package com.aday.pruebapracticavuelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PruebaPracticaVuelos {

  public static void main(String[] args) throws SQLException {
    //mostrarVuelos();
    //mostrarPasajeros();
    //mostrarVuelosPasajeros();
    //mostrarPasajeros("IB-SP-4567");
    //insertarVuelo("ES-NW-337", "27/10/99-11:30", "PERÚ", "MIAMI", 15, 40, 100, 120);
    //borrarVuelo("ES-NW-337");
    //plazasFumadores();
    agregarColumnasPasajeros();
    insertarPasajerosVuelo();
    mostrarPasajeros2("IB-SP-4567");

  }

  public static void mostrarVuelos() {
    try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/AEROLINEAS", "root", "root");) {
      Statement s = c.createStatement();
      ResultSet rs = s.executeQuery(("SELECT * FROM VUELOS"));
      {
        System.out.println("Tabla VUELOS:");
        System.out.printf("%-16s %-16s %-16s %-16s %18s %18s %18s %18s \n",
                "COD_VUELO", "HORA_SALIDA", "DESTINO", "PROCEDENCIA",
                "PLAZAS_FUMADOR", "PLAZAS_NO_FUMADOR", "PLAZAS_TURISTA", "PLAZAS_PRIMERA");
        while (rs.next()) {
          System.out.printf("%-16s %-16s %-16s %-16s %18d %18d %18d %18d \n",
                  rs.getString("COD_VUELO"),
                  rs.getString("HORA_SALIDA"),
                  rs.getString("DESTINO"),
                  rs.getString("PROCEDENCIA"),
                  rs.getInt("PLAZAS_FUMADOR"),
                  rs.getInt("PLAZAS_NO_FUMADOR"),
                  rs.getInt("PLAZAS_TURISTA"),
                  rs.getInt("PLAZAS_PRIMERA"));
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void mostrarPasajeros() {
    try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/AEROLINEAS", "root", "root");) {
      Statement s = c.createStatement();
      ResultSet rs = s.executeQuery(("SELECT * FROM PASAJEROS"));
      {
        System.out.println("\n");
        System.out.println("Tabla PASAJEROS:");
        System.out.printf("%-16s %-16s %-16s %-16s \n",
                "NUM", "COD_VUELO", "TIPO_PLAZA", "FUMADOR");
        while (rs.next()) {
          System.out.printf("%-16s %-16s %-16s %-16s \n",
                  rs.getString("NUM"),
                  rs.getString("COD_VUELO"),
                  rs.getString("TIPO_PLAZA"),
                  rs.getString("FUMADOR"));
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void mostrarVuelosPasajeros() {
    try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/AEROLINEAS", "root", "root");) {
      Statement s = c.createStatement();
      ResultSet rs = s.executeQuery(("SELECT * FROM VUELOS INNER JOIN PASAJEROS ON VUELOS.COD_VUELO = PASAJEROS.COD_VUELO"));
      {
        System.out.println("Tabla VUELOS y PASAJEROS:");
        System.out.printf("%-16s %-16s %-16s %-16s %18s %18s %18s %18s %-16s %-16s %-16s %-16s \n",
                "COD_VUELO", "HORA_SALIDA", "DESTINO", "PROCEDENCIA",
                "PLAZAS_FUMADOR", "PLAZAS_NO_FUMADOR", "PLAZAS_TURISTA", "PLAZAS_PRIMERA",
                "NUM", "COD_VUELO", "TIPO_PLAZA", "FUMADOR");
        while (rs.next()) {
          System.out.printf("%-16s %-16s %-16s %-16s %18d %18d %18d %18d %-16s %-16s %-16s %-16s \n",
                  rs.getString("COD_VUELO"),
                  rs.getString("HORA_SALIDA"),
                  rs.getString("DESTINO"),
                  rs.getString("PROCEDENCIA"),
                  rs.getInt("PLAZAS_FUMADOR"),
                  rs.getInt("PLAZAS_NO_FUMADOR"),
                  rs.getInt("PLAZAS_TURISTA"),
                  rs.getInt("PLAZAS_PRIMERA"),
                  rs.getString("NUM"),
                  rs.getString("COD_VUELO"),
                  rs.getString("TIPO_PLAZA"),
                  rs.getString("FUMADOR"));
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void mostrarPasajeros(String codigoVuelo) {
    try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/AEROLINEAS", "root", "root");) {
      PreparedStatement ps = c.prepareStatement("SELECT * FROM PASAJEROS WHERE COD_VUELO = ?");
      {
        System.out.println("\n");
        System.out.println("Tabla PASAJEROS:");

        ps.setString(1, codigoVuelo);
        ResultSet rs = ps.executeQuery();
        System.out.printf("%-16s %-16s %-16s %-16s \n",
                "NUM", "COD_VUELO", "TIPO_PLAZA", "FUMADOR");
        while (rs.next()) {
          System.out.printf("%-16s %-16s %-16s %-16s \n",
                  rs.getString("NUM"),
                  rs.getString("COD_VUELO"),
                  rs.getString("TIPO_PLAZA"),
                  rs.getString("FUMADOR"));
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void insertarVuelo(String codVuelo, String horaSalida, String destino, String procedencia,
          int plazasFumador, int plazasNoFumador, int plazasTurista, int plazasPrimera) {
    String sql = "INSERT INTO VUELOS (COD_VUELO, HORA_SALIDA, DESTINO, PROCEDENCIA, "
            + "PLAZAS_FUMADOR, PLAZAS_NO_FUMADOR, PLAZAS_TURISTA, PLAZAS_PRIMERA) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/AEROLINEAS", "root", "root"); PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setString(1, codVuelo);
      ps.setString(2, horaSalida);
      ps.setString(3, destino);
      ps.setString(4, procedencia);
      ps.setInt(5, plazasFumador);
      ps.setInt(6, plazasNoFumador);
      ps.setInt(7, plazasTurista);
      ps.setInt(8, plazasPrimera);

      int filasInsertadas = ps.executeUpdate();

      if (filasInsertadas > 0) {
        System.out.println("\n Vuelo insertado correctamente:");
        System.out.printf("%-16s %-16s %-16s %-16s %18s %18s %18s %18s \n",
                "COD_VUELO", "HORA_SALIDA", "DESTINO", "PROCEDENCIA",
                "PLAZAS_FUMADOR", "PLAZAS_NO_FUMADOR", "PLAZAS_TURISTA", "PLAZAS_PRIMERA");
        System.out.printf("%-16s %-16s %-16s %-16s %18d %18d %18d %18d \n",
                codVuelo, horaSalida, destino, procedencia,
                plazasFumador, plazasNoFumador, plazasTurista, plazasPrimera);
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void borrarVuelo(String codVuelo) {
    String sql = "DELETE FROM VUELOS WHERE COD_VUELO = ?";

    try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/AEROLINEAS", "root", "root"); PreparedStatement ps = c.prepareStatement(sql)) {

      ps.setString(1, codVuelo);

      int filasBorradas = ps.executeUpdate();

      if (filasBorradas > 0) {
        System.out.println("\n Vuelo con código '" + codVuelo + "' borrado correctamente.");
      } else {
        System.out.println("\n No se encontró ningún vuelo con el código '" + codVuelo + "'.");
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void plazasFumadores() {
    String sqlUpdate = "UPDATE VUELOS "
            + "SET PLAZAS_NO_FUMADOR = PLAZAS_NO_FUMADOR + PLAZAS_FUMADOR, "
            + "PLAZAS_FUMADOR = 0";

    try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/AEROLINEAS", "root", "root"); Statement s = c.createStatement()) {

      s.executeUpdate(sqlUpdate);

      ResultSet rs = s.executeQuery("SELECT * FROM VUELOS");

      System.out.println("\nTabla VUELOS actualizada:");
      System.out.printf("%-16s %-16s %-16s %-16s %18s %18s %18s %18s \n",
              "COD_VUELO", "HORA_SALIDA", "DESTINO", "PROCEDENCIA",
              "PLAZAS_FUMADOR", "PLAZAS_NO_FUMADOR", "PLAZAS_TURISTA", "PLAZAS_PRIMERA");

      while (rs.next()) {
        System.out.printf("%-16s %-16s %-16s %-16s %18d %18d %18d %18d \n",
                rs.getString("COD_VUELO"),
                rs.getString("HORA_SALIDA"),
                rs.getString("DESTINO"),
                rs.getString("PROCEDENCIA"),
                rs.getInt("PLAZAS_FUMADOR"),
                rs.getInt("PLAZAS_NO_FUMADOR"),
                rs.getInt("PLAZAS_TURISTA"),
                rs.getInt("PLAZAS_PRIMERA"));
      }

    } catch (SQLException e) {
      System.out.println("Error al actualizar o mostrar vuelos: " + e.getMessage());
    }
  }

  public static void agregarColumnasPasajeros() {
    String sqlDNI = "ALTER TABLE PASAJEROS ADD COLUMN IF NOT EXISTS DNI VARCHAR(15)";
    String sqlNombre = "ALTER TABLE PASAJEROS ADD COLUMN IF NOT EXISTS NOMBRE VARCHAR(50)";
    String sqlApellidos = "ALTER TABLE PASAJEROS ADD COLUMN IF NOT EXISTS APELLIDOS VARCHAR(50)";

    try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/AEROLINEAS", "root", "root");
         Statement s = c.createStatement()) {
        s.executeUpdate(sqlDNI);
        s.executeUpdate(sqlNombre);
        s.executeUpdate(sqlApellidos);
        System.out.println("Columnas DNI, NOMBRE y APELLIDOS añadidas correctamente.");
    } catch (SQLException e) {
        System.out.println("Error al agregar columnas: " + e.getMessage());
    }
}

  public static void insertarPasajerosVuelo() {
    String sql = "INSERT INTO PASAJEROS (COD_VUELO, TIPO_PLAZA, FUMADOR, DNI, NOMBRE, APELLIDOS) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/AEROLINEAS", "root", "root"); PreparedStatement ps = c.prepareStatement(sql)) {

      // Primer pasajero
      ps.setString(1, "IB-SP-4567");
      ps.setString(2, "TURISTA");
      ps.setString(3, "NO");
      ps.setString(4, "12345678A");
      ps.setString(5, "Juan");
      ps.setString(6, "Pérez");
      ps.executeUpdate();

      // Segundo pasajero
      ps.setString(1, "IB-SP-4567");
      ps.setString(2, "PRIMERA");
      ps.setString(3, "NO");
      ps.setString(4, "23456789B");
      ps.setString(5, "Ana");
      ps.setString(6, "García");
      ps.executeUpdate();

      // Tercer pasajero
      ps.setString(1, "IB-SP-4567");
      ps.setString(2, "TURISTA");
      ps.setString(3, "SI");
      ps.setString(4, "34567890C");
      ps.setString(5, "Luis");
      ps.setString(6, "Martínez");
      ps.executeUpdate();

      System.out.println("Pasajeros del vuelo IB-SP-4567 insertados correctamente.");

    } catch (SQLException e) {
      System.out.println("Error insertando pasajeros: " + e.getMessage());
    }
  }

  public static void mostrarPasajeros2(String codigoVuelo) {
    try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/AEROLINEAS", "root", "root");) {
      PreparedStatement ps = c.prepareStatement("SELECT * FROM PASAJEROS WHERE COD_VUELO = ?");
      ps.setString(1, codigoVuelo);
      ResultSet rs = ps.executeQuery();

      System.out.println("\nTabla PASAJEROS:");
      System.out.printf("%-4s %-16s %-12s %-8s %-12s %-10s %-12s \n",
              "NUM", "COD_VUELO", "TIPO_PLAZA", "FUMADOR", "DNI", "NOMBRE", "APELLIDOS");

      while (rs.next()) {
        System.out.printf("%-4s %-16s %-12s %-8s %-12s %-10s %-12s \n",
                rs.getInt("NUM"),
                rs.getString("COD_VUELO"),
                rs.getString("TIPO_PLAZA"),
                rs.getString("FUMADOR"),
                rs.getString("DNI"),
                rs.getString("NOMBRE"),
                rs.getString("APELLIDOS"));
      }
    } catch (SQLException e) {
      System.out.println("Error mostrando pasajeros: " + e.getMessage());
    }
  }
}
