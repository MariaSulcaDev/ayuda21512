package pe.edu.vallegrande.importacionesdg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDto {
    private int client_id;
    private String client_type;
    private String full_name;
    private String document_type;
    private String document_number;
    private String phone;
    private String email;
    private String address;
    private String company_name;
    private String company_contact;
    private Date registration_date;
    private String active;


}
