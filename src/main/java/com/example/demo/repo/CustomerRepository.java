package com.example.demo.repo;

import com.example.demo.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByLogin(@Param("login") String login);
}
