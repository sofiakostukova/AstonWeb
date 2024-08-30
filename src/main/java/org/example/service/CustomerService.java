package org.example.service;

import org.example.models.Customer;
import org.example.repository.impl.CustomerRepositoryImpl;

import java.util.List;

public class CustomerService {
    private CustomerRepositoryImpl customerRepository;

    public CustomerService(CustomerRepositoryImpl repository) {
        this.customerRepository = repository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        Customer existingCustomer = getCustomerById(customer.getId());
        existingCustomer.setCustomerType(customer.getCustomerType());
        customerRepository.update(existingCustomer);
    }

    public void deleteCustomer(Integer id) {
        customerRepository.delete(id);
    }
}