package guru.springframework.spring6restmvc.controller;


import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {

    // Object first
    private final CustomerService customerService;



    // CREATE THE CUSTOMER
    @PostMapping
    public ResponseEntity handlePost(@RequestBody Customer customer) {

        Customer savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location" , "/api/v1/customers/" + savedCustomer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);

    }

    // UPDATE BY PATCHIN THE CUSTOMER
    @PatchMapping("{customerId}")
    public ResponseEntity patchCustomerById(@PathVariable("customerId") UUID id, @RequestBody Customer customer) {

        customerService.patchCustomerById(id, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    // UPDATE THE CUSTOMER
    @PutMapping("{customerId}")
    public ResponseEntity updateCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {


        // Calling service impl for internal functionality
        customerService.updateCustomerById(customerId, customer);


        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    // DELETE THE CUSTOMER
    @DeleteMapping("{customerId}")
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID id) {


        customerService.deleteCustomerById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    // LIST ALL THE CUSTOMERS
    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCostumers() {
        return customerService.listCustomer();
    }

    // GET CUSTOMER BY ID
    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {

        log.debug("Getting the customer by id -in the service !!");

        return customerService.getCustomerById(customerId);
    }

}
