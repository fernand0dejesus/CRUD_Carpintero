package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.UUID;
import javax.swing.JTable;
import javax.swing.JTextField;
import Vista.vista;

public class mdl_carpintero {

    //////////////////////1- Parámetros
    private String Nombre_Carpintero;
    private int Edad_Carpintero;
    private int Peso_Carpintero;
    private String Correo_Carpintero;

    // Getters y setters
    public String getNombre_Carpintero() {
        return Nombre_Carpintero;
    }

    public void setNombre_Carpintero(String Nombre_Carpintero) {
        this.Nombre_Carpintero = Nombre_Carpintero;
    }

    public int getEdad_Carpintero() {
        return Edad_Carpintero;
    }

    public void setEdad_Carpintero(int Edad_Carpintero) {
        this.Edad_Carpintero = Edad_Carpintero;
    }

    public int getPeso_Carpintero() {
        return Peso_Carpintero;
    }

    public void setPeso_Carpintero(int Peso_Carpintero) {
        this.Peso_Carpintero = Peso_Carpintero;
    }

    public String getCorreo_Carpintero() {
        return Correo_Carpintero;
    }

    public void setCorreo_Carpintero(String Correo_Carpintero) {
        this.Correo_Carpintero = Correo_Carpintero;
    }

    ////////////////////////3- Métodos 
    public void Guardar() {
        // Obtenemos la conexión
        Connection conexion = ClaseConexion.getConexion();
        try {
            // Definimos la consulta SQL de inserción
            String sql = "INSERT INTO tbCarpintero(UUID_Carpintero, Nombre_Carpintero, Edad_Carpintero, Peso_Carpintero, Correo_Carpintero) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conexion.prepareStatement(sql);

            // Establecemos los valores de los parámetros
            pstmt.setString(1, UUID.randomUUID().toString()); // Generamos un UUID para el carpintero
            pstmt.setString(2, getNombre_Carpintero());
            pstmt.setInt(3, getEdad_Carpintero());
            pstmt.setInt(4, getPeso_Carpintero());
            pstmt.setString(5, getCorreo_Carpintero());

            // Ejecutamos la consulta
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error en el método Guardar: " + ex);
        }
    }

    public void Mostrar(JTable tabla) {
        Connection conexion = ClaseConexion.getConexion();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"UUID_Carpintero", "Nombre_Carpintero", "Edad_Carpintero", "Peso_Carpintero", "Correo_Carpintero"});

        try {
            // Consulta para obtener los datos de la tabla
            String query = "SELECT * FROM tbCarpintero";
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery(query);

            // Recorremos los resultados y los añadimos al modelo de la tabla
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("UUID_Carpintero"),
                    rs.getString("Nombre_Carpintero"),
                    rs.getInt("Edad_Carpintero"),
                    rs.getInt("Peso_Carpintero"),
                    rs.getString("Correo_Carpintero")
                });
            }
            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setWidth(0);

        } catch (SQLException e) {
            System.out.println("Error en el método Mostrar: " + e);
        }
    }

    public void Eliminar(JTable tabla) {
        Connection conexion = ClaseConexion.getConexion();
        int filaSeleccionada = tabla.getSelectedRow();
        String miId = tabla.getValueAt(filaSeleccionada, 0).toString();

        try {
            String sql = "DELETE FROM tbCarpintero WHERE UUID_Carpintero = ?";
            PreparedStatement deleteCarpintero = conexion.prepareStatement(sql);
            deleteCarpintero.setString(1, miId);
            deleteCarpintero.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en el método Eliminar: " + e);
        }
    }

    public void Actualizar(JTable tabla) {
        Connection conexion = ClaseConexion.getConexion();
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada != -1) {
            String miUUID = tabla.getValueAt(filaSeleccionada, 0).toString();

            try {
                String sql = "UPDATE tbCarpintero SET Nombre_Carpintero = ?, Edad_Carpintero = ?, Peso_Carpintero = ?, Correo_Carpintero = ? WHERE UUID_Carpintero = ?";
                PreparedStatement updateCarpintero = conexion.prepareStatement(sql);

                updateCarpintero.setString(1, getNombre_Carpintero());
                updateCarpintero.setInt(2, getEdad_Carpintero());
                updateCarpintero.setInt(3, getPeso_Carpintero());
                updateCarpintero.setString(4, getCorreo_Carpintero());
                updateCarpintero.setString(5, miUUID);
                updateCarpintero.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Error en el método Actualizar: " + e);
            }
        } else {
            System.out.println("No se ha seleccionado ninguna fila.");
        }
    }

    public void Buscar(JTable tabla, JTextField miTextField) {
        Connection conexion = ClaseConexion.getConexion();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"UUID_Carpintero", "Nombre_Carpintero", "Edad_Carpintero", "Peso_Carpintero", "Correo_Carpintero"});

        try {
            String sql = "SELECT * FROM tbCarpintero WHERE Nombre_Carpintero LIKE ?";
            PreparedStatement buscarCarpintero = conexion.prepareStatement(sql);
            buscarCarpintero.setString(1, miTextField.getText() + "%");
            ResultSet rs = buscarCarpintero.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("UUID_Carpintero"),
                    rs.getString("Nombre_Carpintero"),
                    rs.getInt("Edad_Carpintero"),
                    rs.getInt("Peso_Carpintero"),
                    rs.getString("Correo_Carpintero")
                });
            }
            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setWidth(0);

        } catch (SQLException e) {
            System.out.println("Error en el método Buscar: " + e);
        }
    }

    public void limpiar(vista vista) {
        vista.txtNombre2.setText("");
        vista.txtEdad.setText("");
        vista.txtPeso.setText("");
        vista.txtCorreo.setText("");
    }

    public void cargarDatosTabla(vista Vista) {
        int filaSeleccionada = Vista.tbCarpintero.getSelectedRow();

        if (filaSeleccionada != -1) {
            
            String NombreDeTb = Vista.tbCarpintero.getValueAt(filaSeleccionada, 1).toString();
            String EdadDeTb = Vista.tbCarpintero.getValueAt(filaSeleccionada, 2).toString();
            String PesoDeTb = Vista.tbCarpintero.getValueAt(filaSeleccionada, 3).toString();
            String CorreoDeTb = Vista.tbCarpintero.getValueAt(filaSeleccionada, 4).toString();
             
            Vista.txtNombre2.setText(NombreDeTb);
            Vista.txtEdad.setText(EdadDeTb);
            Vista.txtPeso.setText(PesoDeTb);
            Vista.txtCorreo.setText(CorreoDeTb);
        }
    }
}

