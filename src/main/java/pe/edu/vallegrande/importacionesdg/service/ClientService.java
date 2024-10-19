package pe.edu.vallegrande.importacionesdg.service;

import pe.edu.vallegrande.importacionesdg.db.AccesoDB;
import pe.edu.vallegrande.importacionesdg.dto.ClientDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    public List<ClientDto> getAllDtoClient() throws SQLException {
        List<ClientDto> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT *  FROM client WHERE active = '1';";
        try {
            cn = AccesoDB.getConnection();
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ClientDto client = new ClientDto();
                client.setClient_id(rs.getInt("client_id"));
                client.setClient_type(rs.getString("client_type"));
                client.setFull_name(rs.getString("full_name"));
                client.setDocument_type(rs.getString("document_type"));
                client.setDocument_number(rs.getString("document_number"));
                client.setPhone(rs.getString("phone"));
                client.setEmail(rs.getString("email"));
                client.setAddress(rs.getString("address"));
                client.setCompany_name(rs.getString("company_name"));
                client.setCompany_contact(rs.getString("company_contact"));
                client.setRegistration_date(rs.getDate("registration_date"));
                client.setActive(rs.getString("active"));
                list.add(client);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                rs.close();
                ps.close();
                cn.close();
            } catch (Exception e) {
            }
            return list;
        }
    }


    public void insertClient(ClientDto client) throws SQLException {
        Connection cn = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO client (client_type, full_name, document_type, document_number, phone, email, address, company_name, company_contact, registration_date, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            cn = AccesoDB.getConnection();
            ps = cn.prepareStatement(sql);
            ps.setString(1, client.getClient_type());
            ps.setString(2, client.getFull_name());
            ps.setString(3, client.getDocument_type());
            ps.setString(4, client.getDocument_number());
            ps.setString(5, client.getPhone());
            ps.setString(6, client.getEmail());
            ps.setString(7, client.getAddress());
            ps.setString(8, client.getCompany_name());
            ps.setString(9, client.getCompany_contact());
            // Conversión segura de la fecha
            ps.setDate(10, (Date) client.getRegistration_date());
            ps.setString(11, client.getActive());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting Client", e);
        }
    }

    public ClientDto updateClient(ClientDto client) throws SQLException {
        Connection cn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE client SET client_type = ?, full_name = ?, document_type = ?, document_number = ?, phone = ?, email = ?, address = ?, company_name = ?, company_contact = ?, registration_date = ? WHERE client_id = ?;";
        try {
            cn = AccesoDB.getConnection();
            ps = cn.prepareStatement(sql);
            ps.setString(1, client.getClient_type());
            ps.setString(2, client.getFull_name());
            ps.setString(3, client.getDocument_type());
            ps.setString(4, client.getDocument_number());
            ps.setString(5, client.getPhone());
            ps.setString(6, client.getEmail());
            ps.setString(7, client.getAddress());
            ps.setString(8, client.getCompany_name());
            ps.setString(9, client.getCompany_contact());
            ps.setDate(10, (Date) client.getRegistration_date());
            ps.setInt(11, client.getClient_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating Client", e);
        } finally {
            if (ps != null) ps.close();
            if (cn != null) cn.close();
        }
        return client;
    }

    public ClientDto getClientById(int clientId) throws SQLException {
        ClientDto client = null;
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM client WHERE client_id = ? AND active = '1';";
        try {
            cn = AccesoDB.getConnection();
            ps = cn.prepareStatement(sql);
            ps.setInt(1, clientId);
            rs = ps.executeQuery();
            if (rs.next()) {
                client = new ClientDto();
                client.setClient_id(rs.getInt("client_id"));
                client.setClient_type(rs.getString("client_type"));
                client.setFull_name(rs.getString("full_name"));
                client.setDocument_type(rs.getString("document_type"));
                client.setDocument_number(rs.getString("document_number"));
                client.setPhone(rs.getString("phone"));
                client.setEmail(rs.getString("email"));
                client.setAddress(rs.getString("address"));
                client.setCompany_name(rs.getString("company_name"));
                client.setCompany_contact(rs.getString("company_contact"));
                client.setRegistration_date(rs.getDate("registration_date"));
                client.setActive(rs.getString("active"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                rs.close();
                ps.close();
                cn.close();
            } catch (Exception e) {
            }
        }
        return client;
    }

    public void deleteClient(int client_id) throws SQLException {
        Connection cn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE client SET active = '0' WHERE client_id = ?;";
        try {
            cn = AccesoDB.getConnection();
            ps = cn.prepareStatement(sql);
            ps.setInt(1, client_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting Client", e);
        }
    }
}
    
