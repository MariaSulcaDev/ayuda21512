package pe.edu.vallegrande.importacionesdg.prueba.Client;

import pe.edu.vallegrande.importacionesdg.dto.ClientDto;
import pe.edu.vallegrande.importacionesdg.service.ClientService;

import java.sql.SQLException;
import java.util.List;

public class ClientTest {
    public static void main(String[] args) throws SQLException {
        ClientService client = new ClientService();
        List<ClientDto> clientes = client.getAllDtoClient();
        /*
        * Atributos
        * Metodos
        *
        * CLAVE : VALOR
        *
        * DEMO : "Maria"
        */
        for(ClientDto cliente : clientes){
            System.out.println(cliente);
        }
    }
}
