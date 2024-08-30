package org.example;

import org.example.models.Customer;
import org.example.repository.impl.CustomerRepositoryImpl;
import org.example.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class CustomerServiceTest {
    private CustomerService service;

    @Before
    public void setup() {
        Properties props = new Properties();
        try (InputStream is = new FileInputStream("src/test/resources/application-test.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application-test.properties", e);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(props.getProperty("spring.datasource.url"));
        dataSource.setUser(props.getProperty("spring.datasource.username"));
        dataSource.setPassword(props.getProperty("spring.datasource.password"));

        CustomerRepositoryImpl repository = new CustomerRepositoryImpl(dataSource);
        service = new CustomerService(repository);
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer("Test customer type");
        if (customer.getCustomerType() != null && !customer.getCustomerType().trim().isEmpty()) {
            Customer savedCustomer = service.saveCustomer(customer);
            assertNotNull(savedCustomer);
            assertEquals("Test customer type", savedCustomer.getCustomerType());
        } else {
            fail("Customer is null or customer type is null or empty");
        }
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = service.getCustomerById(5);
        assertNotNull(customer);
        customer.setCustomerType("Updated Test customer type");
        service.updateCustomer(customer);
        Customer updatedCustomer = service.getCustomerById(5);
        assertNotNull(updatedCustomer);
        assertEquals("Updated Test customer type", updatedCustomer.getCustomerType());
    }

    @Test
    public void testReadCustomer() {
        Customer customer = service.getCustomerById(5);
        assertNotNull(customer);
        assertEquals("Updated Test customer type", customer.getCustomerType());
    }

    @Test
    public void testDeleteCustomer() {
        Customer customerBeforeDeletion = service.getCustomerById(5);
        assertNotNull(customerBeforeDeletion);
        service.deleteCustomer(5);
    }

    @Test
    public void testReadAllCustomers() {
        List<Customer> customers = service.getAllCustomers();
        assertNotNull(customers);
        assertTrue(!customers.isEmpty());
    }
}