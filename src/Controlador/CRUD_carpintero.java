package Controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import modelo.mdl_carpintero;
import Vista.vista;

public class CRUD_carpintero implements MouseListener, KeyListener {

    //////////////////////////2- Parámetros
    private mdl_carpintero Modelo;
    private vista Vista;

    //////////////////////////3- Constructor de la clase
    public CRUD_carpintero(mdl_carpintero modelo, vista vista) {
        this.Modelo = modelo;
        this.Vista = vista;

        // Asociar eventos de mouse a los botones
        vista.btnAgregar.addMouseListener(this);
        vista.btnEliminar.addMouseListener(this);
        vista.btnAgregar.addMouseListener(this);
        vista.txtNombre2.addKeyListener(this);
        vista.tbCarpintero.addMouseListener(this);

        modelo.Mostrar(vista.tbCarpintero);
    }

    ///////////////////////////////////////// Eventos
    @Override
    public void mouseClicked(MouseEvent e) {
        //////////////////////////4- Detección de clicks en la vista
        if (e.getSource() == Vista.btnAgregar) {
            if (Vista.txtNombre2.getText().isEmpty() || Vista.txtEdad.getText().isEmpty() || Vista.txtPeso.getText().isEmpty() || Vista.txtCorreo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vista, "Debes llenar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    // Asignar datos de la vista al modelo
                    Modelo.setNombre_Carpintero(Vista.txtNombre2.getText());
                    Modelo.setEdad_Carpintero(Integer.parseInt(Vista.txtEdad.getText()));
                    Modelo.setPeso_Carpintero(Integer.parseInt(Vista.txtPeso.getText()));
                    Modelo.setCorreo_Carpintero(Vista.txtCorreo.getText());

                    // Ejecutar el método para guardar
                    Modelo.Guardar();
                    Modelo.Mostrar(Vista.tbCarpintero);
                    Modelo.limpiar(Vista);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Vista, "La edad y el peso deben ser números", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        if (e.getSource() == Vista.btnEliminar) {
            if (Vista.tbCarpintero.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(Vista, "Debes seleccionar un registro para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Modelo.Eliminar(Vista.tbCarpintero);
                Modelo.Mostrar(Vista.tbCarpintero);
                Modelo.limpiar(Vista);
            }
        }

        if (e.getSource() ==Vista.btnAgregar) {
            if (Vista.tbCarpintero.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(Vista, "Debes seleccionar un registro para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    // Asignar datos de la vista al modelo
                    Modelo.setNombre_Carpintero(Vista.txtNombre2.getText());
                    Modelo.setEdad_Carpintero(Integer.parseInt(Vista.txtEdad.getText()));
                    Modelo.setPeso_Carpintero(Integer.parseInt(Vista.txtPeso.getText()));
                    Modelo.setCorreo_Carpintero(Vista.txtCorreo.getText());

                    // Ejecutar el método para actualizar
                    Modelo.Actualizar(Vista.tbCarpintero);
                    Modelo.Mostrar(Vista.tbCarpintero);
                    Modelo.limpiar(Vista);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Vista, "La edad y el peso deben ser números", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        if (e.getSource() == Vista.tbCarpintero) {
            Modelo.cargarDatosTabla(Vista);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == Vista.txtNombre2) {
            Modelo.Buscar(Vista.tbCarpintero, Vista.txtNombre2);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
}
