package org.unibl.etf.rest_api.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvoiceID", nullable = false, updatable = false, unique = true, columnDefinition = "serial")
    private int invoiceID;

    @Column(name = "RentalID", nullable = false, columnDefinition = "int")
    private int rentalID;

    @Column(name = "Amount", nullable = false, columnDefinition = "decimal(10,2)")
    private double amount;
}
