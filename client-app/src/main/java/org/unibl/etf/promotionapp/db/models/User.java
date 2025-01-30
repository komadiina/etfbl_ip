package org.unibl.etf.promotionapp.db.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID", updatable = false, unique = true, nullable = false, columnDefinition = "serial")
    private int id;

    @Column(name = "Username", unique = true, nullable = false, columnDefinition = "varchar(50)")
    private String username;

    @Column(name = "Password", nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Column(name = "FirstName", nullable = false, columnDefinition = "varchar(50)")
    private String firstName;

    @Column(name = "LastName", nullable = false, columnDefinition = "varchar(50)")
    private String lastName;

    @Column(name = "Email", nullable = false, columnDefinition = "varchar(100)", unique = true)
    private String email;

    @Column(name = "PhoneNumber", columnDefinition = "varchar(20)")
    private String phoneNumber;

    @Column(name = "UserType", nullable = false, columnDefinition = "enum('Client', 'Employee')")
    private String userType;

    @Column(name = "IsActive", nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean active;


    public User obscure() {
        this.password = "<obscured>";
        return this;
    }
}
