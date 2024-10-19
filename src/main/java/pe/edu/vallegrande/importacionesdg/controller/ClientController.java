package pe.edu.vallegrande.importacionesdg.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.vallegrande.importacionesdg.dto.ClientDto;
import pe.edu.vallegrande.importacionesdg.service.ClientService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@WebServlet({"/clientes", "/crearCliente", "/actualizarCliente", "/eliminarCliente", "/recuperarCliente", "/getClient"})
public class ClientController extends HttpServlet {

    ClientService clientService = new ClientService();
    ClientDto clientDto = new ClientDto();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/crearCliente":
                createClient(req, resp);
                break;
            case "/actualizarCliente":
                updateClient(req, resp);
                break;
            case "/eliminarCliente":
                deleteClient(req, resp);
                break;
        }
    }

    private void updateClient(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int clientId = Integer.parseInt(req.getParameter("client_id"));
        String clientType = req.getParameter("client_type");
        String fullName = req.getParameter("full_name");
        String documentType = req.getParameter("document_type");
        String documentNumber = req.getParameter("document_number");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String companyName = req.getParameter("company_name");
        String companyContact = req.getParameter("company_contact");
        String registrationDateStr = req.getParameter("registration_date");

        Date registrationDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            registrationDate = sdf.parse(registrationDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ClientDto clientDto = new ClientDto();
        clientDto.setClient_id(clientId);
        clientDto.setClient_type(clientType);
        clientDto.setFull_name(fullName);
        clientDto.setDocument_type(documentType);
        clientDto.setDocument_number(documentNumber);
        clientDto.setPhone(phone);
        clientDto.setEmail(email);
        clientDto.setAddress(address);
        clientDto.setCompany_name(companyName);
        clientDto.setCompany_contact(companyContact);
        clientDto.setRegistration_date(new java.sql.Date(registrationDate.getTime()));

        try {
            clientService.updateClient(clientDto);
            resp.sendRedirect(req.getContextPath() + "/clientes");
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to update client");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    private void createClient(HttpServletRequest req, HttpServletResponse resp) {
        clientDto.setClient_type(req.getParameter("client_type"));
        clientDto.setFull_name(req.getParameter("full_name"));
        clientDto.setDocument_type(req.getParameter("document_type"));
        clientDto.setDocument_number(req.getParameter("document_number"));
        clientDto.setPhone(req.getParameter("phone"));
        clientDto.setEmail(req.getParameter("email"));
        clientDto.setAddress(req.getParameter("address"));
        clientDto.setCompany_name(req.getParameter("company_name"));
        clientDto.setCompany_contact(req.getParameter("company_contact"));

        // Manejo de la fecha con formato adecuado
        String acquisitionDate = req.getParameter("registration_date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parsedDate = sdf.parse(acquisitionDate);  // Convertir la fecha del parámetro
            clientDto.setRegistration_date(new java.sql.Date(parsedDate.getTime())); // Convertir a java.sql.Date
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing acquisition_date: " + acquisitionDate, e);
        }

        clientDto.setActive(String.valueOf(1));

        try {
            clientService.insertClient(clientDto);
            resp.sendRedirect("/clientes");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/clientes":
                try {
                    getAllClient(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
                case "/getClient":
                    getClientById(req, resp);
                    break;
        }
    }

    private void deleteClient(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int clientId = Integer.parseInt(req.getParameter("client_id"));
        try {
            clientService.deleteClient(clientId);
            resp.sendRedirect(req.getContextPath() + "/clientes");
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to delete client");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    private void getAllClient(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<ClientDto> clientList;
        clientList = clientService.getAllDtoClient();
        req.setAttribute("client", clientList);
        req.getRequestDispatcher("/Clients/Client.jsp").forward(req, resp);

    }

    private void getClientById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int clientId = Integer.parseInt(req.getParameter("client_id"));
        try {
            ClientDto client = clientService.getClientById(clientId);
            req.setAttribute("client", client);
            req.getRequestDispatcher("/Clients/Client.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to retrieve client");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

}
