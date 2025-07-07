package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import guru.springframework.spring6restmvc.services.CustomerService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CustomerControllerIT {

    // IoC
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;


    @Test
    void testGetByIdNotFound() {

        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetBeerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());

        assertThat(customerDTO).isNotNull();

    }

    @Test
    void testListCustomers() {
        List<CustomerDTO> dtos = customerController.listCostumers();
        assertThat(dtos.size()).isEqualTo(3);

    }


    @Transactional
    @Rollback
    @Test
    void testEmptyList() {

        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listCostumers();
        assertThat(dtos.size()).isEqualTo(0);
    }
}
