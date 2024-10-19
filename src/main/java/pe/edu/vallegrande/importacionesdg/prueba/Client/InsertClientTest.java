package pe.edu.vallegrande.importacionesdg.prueba.Client;

import pe.edu.vallegrande.importacionesdg.dto.ClientDto;
import pe.edu.vallegrande.importacionesdg.service.ClientService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class InsertClientTest {
    public static void main(String[] args) throws SQLException {
        ClientService service = new ClientService();
        ClientDto client = new ClientDto();

        client.setClient_type("E");
        client.setFull_name("Juan Perez");
        client.setDocument_type("DNI");
        client.setDocument_number("12345678");
        client.setPhone("987654321");
        client.setEmail("juan.perez@gmail.com");
        client.setAddress("Jr. Los Pinos 123");
        client.setCompany_name("Empresa SAC");
        client.setCompany_contact("Maria");
        client.setRegistration_date(Date.valueOf(LocalDate.now()));
        client.setActive("A");

        service.insertClient(client);

        System.out.println("Client insertado");

    }
}
