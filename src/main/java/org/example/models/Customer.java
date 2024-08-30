package org.example.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Customer")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Customer", discriminatorType = DiscriminatorType.STRING)
public class Customer extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "customer_type")
    private String customerType;

    public Customer(String test_customer_type) {
        this.customerType= test_customer_type;
    }

    public Customer() {

    }

    public Customer(int id, String customerType) {
        this.id = id;
        this.customerType = customerType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
}