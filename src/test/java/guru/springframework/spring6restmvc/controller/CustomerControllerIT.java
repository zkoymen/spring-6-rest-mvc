package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//  Test-Driven Development
// add tests first, after modify and refactor rest of the code
@SpringBootTest
class CustomerControllerIT {

    // IoC
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerMapper customerMapper;


    @Test
    void testPatchByIdNotFound() {

        assertThrows(NotFoundException.class,() -> {
           customerController.patchCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });

    }

    @Rollback
    @Transactional
    @Test
    void testPatchById() {

        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);

        // change data partially
        customerDTO.setCustomerName("Ali Hakkı Topbaykuş");

        // Get response entity
        ResponseEntity responseEntity = customerController.patchCustomerById(customer.getId(), customerDTO);

        // Check returned status code
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        // Create updated customer entity and
        Customer patchedCustomer = customerRepository.findById(customer.getId()).get();

        // check if it s the same name
        assertThat(patchedCustomer.getCustomerName()).isEqualTo(customer.getCustomerName());
    }

    @Test
    void testDeleteNotFound() {

        assertThrows(NotFoundException.class, () -> {
           customerController.deleteCustomerById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteById() {

        // Deleting the 1st element for the example
        Customer customer = customerRepository.findAll().get(0);

        // No need to translate it to DTO --> no carrying data around
        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(customerRepository.findById(customer.getId())).isEmpty();

    }

    @Test
    void testUpdateNotFound() {

        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateCustomer() {

        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);

        customerDTO.setId(null);
        customerDTO.setVersion(null);

        final String name  = "Ali Hakkı Topbaykuş";

        customerDTO.setCustomerName(name);

        ResponseEntity responseEntity = customerController.updateCustomerById(customer.getId(),
                customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Customer updatedCustomer  = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(name);
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewCustomer() {

        // Build new object
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("New Customer")
                .build();

        ResponseEntity responseEntity = customerController.handlePost(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        // extract customerId part and check if it is really created or not --> in db
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");

        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer customer = customerRepository.findById(savedUUID).get();

        assertThat(customer).isNotNull();

    }

    @Test
    void testGetByIdNotFound() {

        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetCustomerById() {
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

