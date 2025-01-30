package org.unibl.etf.promotionapp.db.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "ClientID")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Client extends User {
    @Column(name = "IDCardNumber", columnDefinition = "varchar(20) unique not null")
    private String idCardNumber;

    @Column(name = "PassportID", columnDefinition = "varchar(20)")
    private String passportID;

    @Column(name = "Balance", nullable = false, columnDefinition = "decimal(10,2) not null default 0.00")
    private double balance = 0.0;
}
