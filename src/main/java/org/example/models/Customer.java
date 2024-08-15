package org.example.models;

import jakarta.persistence.Column;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.DiscriminatorValue;

@Entity
@Table(name = "customers")
@DiscriminatorValue("Customer")
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
    @Column(name = "customer_type")
    private String customerType;

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}