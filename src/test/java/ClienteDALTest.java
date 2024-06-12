import accesoadatos.ClienteDAL;
import entidadesdenegocio.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class ClienteDALTest {
    @Test
    public void guardarTest() throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setNombre("Orlando Enrique");
        cliente.setApellido("Solis Aguilar");
        cliente.setTelefono("6958-6475");
        cliente.setDireccion("Canton Talcomunca");

        int esperado = 1;
        int actual = ClienteDAL.guardar(cliente);
        Assertions.assertEquals(esperado, actual);
    }

    @Test
    public void obtenerTodosTest() throws SQLException {
        int esperado = 2;
        int actual = ClienteDAL.obtenerTodos().size();
        Assertions.assertEquals(esperado, actual);
    }

    @Test
    public void modificarTest() throws SQLException{
        Cliente cliente = new Cliente();
        cliente.setId(2);
        cliente.setNombre("Orlando Enrique");
        cliente.setApellido("Solis Aguilar");
        cliente.setTelefono("7654-7688");
        cliente.setDireccion("Nahuizalco");

        int esperado = 1;
        int actual = ClienteDAL.modificar(cliente);
        Assertions.assertEquals(esperado, actual);
    }

    @Test
    public void eliminarTest() throws SQLException{
        Cliente cliente = new Cliente();
        cliente.setId(1);

        int esperado = 1;
        int actual = ClienteDAL.eliminar(cliente);
        Assertions.assertEquals(esperado, actual);
    }

    @Test
    public void obtenerDatosFiltradosTest() throws SQLException{
        Cliente cliente = new Cliente();
        cliente.setNombre("Orlando Solis");
        cliente.setId(0);
        cliente.setDireccion("Sonzacate");

        int actual = ClienteDAL.obtenerDatosFiltrados(cliente).size();
        Assertions.assertNotEquals(0, actual);
    }
}

