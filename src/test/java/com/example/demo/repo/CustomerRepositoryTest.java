package com.example.demo.repo;

import com.example.demo.models.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findAlreadyExistCustomerByLogin() {
        Customer customer = new Customer("admin");
        Customer found = customerRepository.findByLogin(customer.getLogin());
        Assertions.assertEquals(customer.getLogin(), found.getLogin());
    }

    @Test
    void nullWhenCustomerNotExist() {
        Customer customer = new Customer("customerThatShouldNotBeHere");
        Customer found = customerRepository.findByLogin(customer.getLogin());
        Assertions.assertNull(found);
    }
}