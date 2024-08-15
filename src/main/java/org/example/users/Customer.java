package org.example.users;
import jakarta.persistence.*;

@DiscriminatorValue("customer")
@Entity
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