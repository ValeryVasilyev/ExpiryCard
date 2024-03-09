package com.example.demo.repo;

import com.example.demo.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByLogin(@Param("login") String login);
}
