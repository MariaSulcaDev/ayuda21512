package pe.edu.vallegrande.importacionesdg.prueba.Client;

import pe.edu.vallegrande.importacionesdg.dto.ClientDto;
import pe.edu.vallegrande.importacionesdg.service.ClientService;

import java.sql.SQLException;

public class UpdateClient {
    public static void main(String[] args) throws SQLException {
        ClientService service = new ClientService();
        ClientDto client = new ClientDto();
        client.setClient_id(15);
        service.deleteClient(client.getClient_id());

    }
}
