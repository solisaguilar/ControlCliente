package accesoadatos;

import entidadesdenegocio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAL {
    // método que muestra todos los registros de la tabla
    public static ArrayList<Cliente> obtenerTodos() throws SQLException {
        ArrayList<Cliente> lista = new ArrayList<>();
        Cliente cliente;
        try{
            String sql = "SELECT Id, Nombre, Apellido, Telefono, Direccion FROM Clientes";
            Connection conexion = ComunDB.obtenerConexion();
            PreparedStatement ps = ComunDB.crearPreparedStatement(conexion, sql);
            ResultSet rs = ComunDB.obtenerResultSet(ps);
            while (rs.next()){
                cliente = new Cliente(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5));
                lista.add(cliente);
            }
            conexion.close();
            ps.close();
            rs.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return lista;
    }

    // método que permite guardar un nuevo registro
    public static int guardar(Cliente cliente) throws SQLException{
        int result = 0;
        try {
            String sql = "INSERT INTO Clientes(Nombre, Apellido, Telefono, Direccion ) VALUES(?, ?, ?, ?)";
            Connection conexion = ComunDB.obtenerConexion();
            PreparedStatement ps = ComunDB.crearPreparedStatement(conexion, sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getDireccion());
            result = ps.executeUpdate();
            conexion.close();
            ps.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    // método que permite modificar un registro existente
    public static int modificar(Cliente cliente) throws SQLException{
        int result = 0;
        try {
            String sql = "UPDATE Clientes SET Nombre = ?,  Apellido = ?, Telefono = ?, Direccion = ? WHERE Id = ?";
            Connection conexion = ComunDB.obtenerConexion();
            PreparedStatement ps = ComunDB.crearPreparedStatement(conexion, sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getDireccion());
            ps.setInt(5, cliente.getId());
            result = ps.executeUpdate();
            conexion.close();
            ps.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    // método que permite eliminar un registro existente
    public static int eliminar(Cliente cliente) throws SQLException{
        int result = 0;
        try {
            String sql = "DELETE FROM Clientes WHERE Id = ?";
            Connection conexion = ComunDB.obtenerConexion();
            PreparedStatement ps = ComunDB.crearPreparedStatement(conexion, sql);
            ps.setInt(1, cliente.getId());
            result = ps.executeUpdate();
            conexion.close();
            ps.close();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    // método para consultar mediante criterios
    public static ArrayList<Cliente> obtenerDatosFiltrados(Cliente cliente) throws SQLException{
        ArrayList<Cliente> lista = new ArrayList<>();
        Cliente est;
        try{
            String sql = "SELECT id, nombre, apellido, Telefono, Direccion FROM Clientes WHERE id = ? or apellido like'%" + cliente.getApellido() + "%' or Telefono like'%" + cliente.getTelefono() + "%'";
            Connection connection = ComunDB.obtenerConexion();
            PreparedStatement ps = ComunDB.crearPreparedStatement(connection, sql);
            ps.setInt(1, cliente.getId());
            ResultSet rs = ComunDB.obtenerResultSet(ps);
            while (rs.next()){
                est = new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                lista.add(est);
            }
            connection.close();
            ps.close();
            rs.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return lista;
    }
}


