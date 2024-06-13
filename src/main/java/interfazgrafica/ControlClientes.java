package interfazgrafica;

import entidadesdenegocio.Cliente;
import logicadenegocio.ClienteBL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControlClientes  extends JFrame {
    private JPanel jpPrincipal;
    private JTextField txtId;
    private JTextField textNombre;
    private JTextField textApellido;
    private JTextField textTelefono;
    private JTextField textDireccion;
    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnCancelar;
    private JButton btnSalir;
    private JRadioButton rdbId;
    private JRadioButton rdbApellido;
    private JRadioButton rdbDireccion;
    private JTextField txtCriterio;
    private JButton btnBuscar;
    private JTable tbClientes;
    private ButtonGroup criterioBusqueda;

    // instancias propias (creadas por nosotros)
    ArrayList<Cliente> listClientes;
    Cliente client;
    ClienteBL ClienteBL = new ClienteBL();

    // declaración del método main, permite que clase sea ejecutable
    public static void main(String[] args) throws SQLException {
        new ControlClientes();
    }

    // Método constructor que llama a los métodos que inicializan la ventana y dan estado inicial al formulario.
    // También están las sobrescrituras para dar la funcionalidad a los botones.
    public ControlClientes() throws SQLException {
        inicializar();

        // funcionalidad del botón Nuevo
        btnNuevo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                txtId.setEnabled(true);
                textNombre.setEnabled(true);
                textApellido.setEnabled(true);
                textTelefono.setEnabled(true);
                textDireccion.setEnabled(true);
                btnGuardar.setEnabled(true);
                btnNuevo.setEnabled(false);
                btnCancelar.setEnabled(true);
            }
        });

        // funcionalidad del botón Guardar
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                client = new Cliente();
                client.setId(Integer.parseInt(txtId.getText()));
                client.setNombre(textNombre.getText());
                client.setTelefono(textTelefono.getText());
                client.setApellido(textApellido.getText());
                client.setDireccion(textDireccion.getText());

                try{
                    ClienteBL.guardar(client);
                    JOptionPane.showMessageDialog(null, "Se guardó correctamente");
                    actualizarForm();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // funcionalidad del botón Salir
        btnSalir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });

        // funcionalidad del botón Cancelar
        btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    actualizarForm();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // funcionalidad del clic sobre la Tabla
        tbClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int fila = tbClientes.getSelectedRow();
                txtId.setText(tbClientes.getValueAt(fila, 0).toString());
                textNombre.setText(tbClientes.getValueAt(fila, 1).toString());
                textApellido.setText(tbClientes.getValueAt(fila, 2).toString());
                textTelefono.setText(tbClientes.getValueAt(fila, 3).toString());
                textDireccion.setText(tbClientes.getValueAt(fila, 4).toString());

                textNombre.setEnabled(true);
                textApellido.setEnabled(true);
                textTelefono.setEnabled(true);
                textDireccion.setEnabled(true);
                txtId.setEnabled(true);

                btnNuevo.setEnabled(false);
                btnModificar.setEnabled(true);
                btnEliminar.setEnabled(true);
                btnCancelar.setEnabled(true);
            }
        });

        // funcionalidad del botón Modificar
        btnModificar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                client = new Cliente();
                client.setId(Integer.parseInt(txtId.getText()));
                client.setNombre(textNombre.getText());
                client.setApellido(textApellido.getText());
                client.setTelefono(textTelefono.getText());
                client.setDireccion(textDireccion.getText());
                try {
                    ClienteBL.modificar(client);
                    JOptionPane.showMessageDialog(null, "Se modificó con éxito");
                    actualizarForm();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // funcionalidad del botón Eliminar
        btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                client = new Cliente();
                client.setId(Integer.parseInt(txtId.getText()));
                try{
                    ClienteBL.eliminar(client);
                    JOptionPane.showMessageDialog(null, "Se eliminó correctamente");
                    actualizarForm();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // funcionalidad del botón Buscar
        btnBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(txtCriterio.getText().equals("") || (!rdbId.isSelected() &&
                        !rdbApellido.isSelected() && !rdbDireccion.isSelected()) ){
                    JOptionPane.showMessageDialog(null,
                            "Seleccione un criterio de búsqueda o escriba el valor a buscar");
                }

                client = new Cliente();

                if(rdbId.isSelected()){
                    client.setId(Integer.parseInt(txtCriterio.getText()));
                    try{
                        llenarTabla(ClienteBL.obtenerDatosFiltrados(client));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if(rdbApellido.isSelected()){
                    client.setApellido(txtCriterio.getText());
                    try{
                        llenarTabla(ClienteBL.obtenerDatosFiltrados(client));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if(rdbDireccion.isSelected()){
                    client.setDireccion(txtCriterio.getText());
                    try{
                        llenarTabla(ClienteBL.obtenerDatosFiltrados(client));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    // método para inicializar la ventana
    void inicializar() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 700);
        setTitle("Control de Clientes");
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        criterioBusqueda = new ButtonGroup();
        criterioBusqueda.add(rdbId);
        criterioBusqueda.add(rdbApellido);
        criterioBusqueda.add(rdbDireccion);

        setContentPane(jpPrincipal);
        setVisible(true);
    }

    // método para llenar la tabla con los datos obtenidos de la base de datos
    void llenarTabla(ArrayList<Cliente> clientes) {
        Object[] obj = new Object[5];
        listClientes = new ArrayList<>();
        String[] encabezado = {"ID", "Nombre", "Apellido", "Telefono", "Direccion"};
        DefaultTableModel tm = new DefaultTableModel(null, encabezado);
        listClientes.addAll(clientes);
        for (Cliente item : listClientes) {
            obj[0] = item.getId();
            obj[1] = item.getNombre();
            obj[2] = item.getApellido();
            obj[3] = item.getTelefono();
            obj[4] = item.getDireccion();

            tm.addRow(obj);
        }
        tbClientes.setModel(tm);
    }

    // método para dar estado inicial al formulario
    void actualizarForm() throws SQLException {
        txtId.setText("");
        textNombre.setText("");
        textApellido.setText("");
        textTelefono.setText("");
        textDireccion.setText("");

        txtId.setEnabled(false);
        textNombre.setEnabled(false);
        textApellido.setEnabled(false);
        textTelefono.setEnabled(false);
        textDireccion.setEnabled(false);

        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);

        criterioBusqueda.clearSelection();
        txtCriterio.setText("");

        llenarTabla(ClienteBL.obtenerTodos());
    }
}
