package org.unibl.etf.rest_api.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "employees")
@PrimaryKeyJoinColumn(name = "EmployeeID")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User {
    @Column(name = "Role", nullable = false, columnDefinition = "enum('Administrator', 'Operator', 'Manager')")
    private String role;
}
