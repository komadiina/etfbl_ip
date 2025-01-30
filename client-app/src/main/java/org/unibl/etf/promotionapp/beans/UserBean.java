package org.unibl.etf.promotionapp.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private String idCardNumber;
    private String firstName;
    private String lastName;
    private String passportID;
}
