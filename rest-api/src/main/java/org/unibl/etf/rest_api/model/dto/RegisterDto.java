package org.unibl.etf.rest_api.model.dto;

import lombok.*;
import org.unibl.etf.rest_api.model.db.Client;
import org.unibl.etf.rest_api.model.db.User;

// encapsulates fields from 'User' and 'Client'
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterDto {
    // user
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String userType;

    // client
    private String idCardNumber;
    private String passportID;
}
