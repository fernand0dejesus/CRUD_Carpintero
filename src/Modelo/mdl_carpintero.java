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


    //////////////////////1- Parametros
    
    private String Nombre_Carpintero;
    private int Edad_Carpintero;
    private int Peso_Carpintero;
    private String Correo_Carpintero;

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
    

    ////////////////////////2- Metodos get y set
    

    ////////////////////////3- Métodos 
    public void Guardar() {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClaseConexion.getConexion();
        try {
            //Variable que contiene la Query a ejecutar
            String sql = "INSERT INTO tbCarpintero(UUID_Carpintero, Nombre_Carpintero, Edad_Carpintero, Peso_Carpintero, Correo_Carpintero) VALUES (?, ?, ?, ?, ?)";
            //Creamos el PreparedStatement que ejecutará la Query
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            //Establecer valores de la consulta SQL
            pstmt.setString(1, UUID_Carpintero.randomUUID().toString());
            pstmt.setString(2, getNombre_Carpintero());
            pstmt.setInt(3, getEdad_Carpintero());
            pstmt.setInt(4, getPeso_Carpintero());
            pstmt.setString(5, getCorreo_Carpintero());

            //Ejecutar la consulta
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("este es el error en el modelo:metodo guardar " + ex);
        }
    }

    public void Mostrar(JTable tabla) {
        //Creamos una variable de la clase de conexion
        Connection conexion = ClaseConexion.getConexion();
        //Definimos el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"UUID_Carpintero", "Nombre_Carpintero", "Edad_Carpintero", "Peso_Carpintero", "Correo_Carpintero"});
        try {
            //Consulta a ejecutar
            String query = "SELECT * FROM tbCarpintero";
            //Creamos un Statement
            Statement statement = conexion.createStatement();
            //Ejecutamos el Statement con la consulta y lo asignamos a una variable de tipo ResultSet
            ResultSet rs = statement.executeQuery(query);
            //Recorremos el ResultSet
            while (rs.next()) {
                //Llenamos el modelo por cada vez que recorremos el resultSet
                modelo.addRow(new Object[]{rs.getString("UUID_Carpintero"), 
                    rs.getString("Nombre_Carpintero"), 
                    rs.getInt("Edad_Carpintero"), 
                    rs.getString("Peso_Carpintero"),
                    rs.getString("Correo_Carpintero")});
                                                   
                
            }
            //Asignamos el nuevo modelo lleno a la tabla
            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setWidth(0);
        } catch (Exception e) {
            System.out.println("Este es el error en el modelo, metodo mostrar " + e);
        }
    }

    public void Eliminar(JTable tabla) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClaseConexion.getConexion();

        //obtenemos que fila seleccionó el usuario
        int filaSeleccionada = tabla.getSelectedRow();
        //Obtenemos el id de la fila seleccionada

        String miId = tabla.getValueAt(filaSeleccionada, 0).toString();
        //borramos 
        try {
            String sql = "delete from tbCarpintero where UUID_Carpintero = ?";
            PreparedStatement deleteEstudiante = conexion.prepareStatement(sql);
            deleteEstudiante.setString(1, miId);
            deleteEstudiante.executeUpdate();
        } catch (Exception e) {
            System.out.println("este es el error metodo de eliminar" + e);
        }
    }

    public void Actualizar(JTable tabla) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClaseConexion.getConexion();

        //obtenemos que fila seleccionó el usuario
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada != -1) {
            //Obtenemos el id de la fila seleccionada
            String miUUId = tabla.getValueAt(filaSeleccionada, 0).toString();

            try {
                //Ejecutamos la Query
                String sql = "update tbCarpintero set Nombre_Carpintero= ?, Edad_Carpintero = ?, Peso_Carpintero = ?, Correo_Carpintero where UUID = ?";
                PreparedStatement updateUser = conexion.prepareStatement(sql);

                updateUser.setString(1, getNombre_Carpintero());
                updateUser.setInt(2, getEdad_Carpintero());
                updateUser.setInt(3, getPeso_Carpintero());
                updateUser.setString(3, getCorreo_Carpintero());
                updateUser.setString(4, miUUId);
                updateUser.executeUpdate();

            } catch (Exception e) {
                System.out.println("este es el error en el metodo de actualizar" + e);
            }
        } else {
            System.out.println("no");
        }
    }

    public void Buscar(JTable tabla, JTextField miTextField) {
        //Creamos una variable igual a ejecutar el método de la clase de conexión
        Connection conexion = ClaseConexion.getConexion();

        //Definimos el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"UUID_Carpintero", "Nombre_Carpintero", "Edad_Carpintero", "Peso_Carpintero","Correo_Carpintero"});
        try {
            String sql = "SELECT * FROM tbCarpintero WHERE nombre LIKE ? || '%'";
            PreparedStatement deleteEstudiante = conexion.prepareStatement(sql);
            deleteEstudiante.setString(1, miTextField.getText());
            ResultSet rs = deleteEstudiante.executeQuery();

            while (rs.next()) {
                //Llenamos el modelo por cada vez que recorremos el resultSet
                modelo.addRow(new Object[]{rs.getString("UUID_Carpintero"), rs.getString("Nombre_Carpintero"), rs.getInt("Edad_Carpintero"), rs.getInt("Peso_Carpintero"),rs.getString("Correo_Carpintero")});
            }

            
            //Asignamos el nuevo modelo lleno a la tabla
            tabla.setModel(modelo);
            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setWidth(0);
        } catch (Exception e) {
            System.out.println("Este es el error en el modelo, metodo de buscar " + e);
        }
    }

    public void limpiar(vista vista) {
        vista.txtNombre2.setText("");
        vista.txtEdad.setText("");
        vista.txtPeso.setText("");
        vista.txtCorreo.setText("");
    }

    public void cargarDatosTabla(vista Vista) {
        // Obtén la fila seleccionada 
        var filaSeleccionada = Vista.tbCarpintero.getSelectedRow();

        // Debemos asegurarnos que haya una fila seleccionada antes de acceder a sus valores
        if (filaSeleccionada != -1) {
            String UUIDDeTB = Vista.tbCarpintero.getValueAt(filaSeleccionada, 0).toString();
            String NombreDeTb = Vista.tbCarpintero.getValueAt(filaSeleccionada, 1).toString();
            String EdadDeTb = Vista.tbCarpintero.getValueAt(filaSeleccionada, 2).toString();
            String PesoDeTb = Vista.tbCarpintero.getValueAt(filaSeleccionada, 3).toString();
            String CorreoDeTb = Vista.tbCarpintero.getValueAt(filaSeleccionada, 4).toString();

            // Establece los valores en los campos de texto
            Vista.txtNombre2.setText(NombreDeTb);
            Vista.txtEdad.setText(EdadDeTb);
            Vista.txtPeso.setText(PesoDeTb);
             Vista.txtCorreo.setText(CorreoDeTb);
        }
    }

}
