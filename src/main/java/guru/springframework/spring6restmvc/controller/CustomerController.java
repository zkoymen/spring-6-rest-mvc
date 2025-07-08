package guru.springframework.spring6restmvc.controller;


import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping
public class CustomerController {

    // Defining paths as constants
    // Add "/" after --> or you will get errors şn header get location part
    public static final String CUSTOMER_PATH = "/api/v1/customers/";
    public static final String CUSTOMER_PATH_ID = "/api/v1/customers" + "/{customerId}" ;


    // Object first
    private final CustomerService customerService;



    // CREATE THE CUSTOMER
    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity handlePost(@RequestBody CustomerDTO customer) {

        CustomerDTO savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location" , CUSTOMER_PATH + savedCustomer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);

    }

    // UPDATE BY PATCHING THE CUSTOMER
    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity patchCustomerById(@PathVariable("customerId") UUID id, @RequestBody CustomerDTO customer) {

        if(customerService.patchCustomerById(id, customer).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    // UPDATE THE CUSTOMER
    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomerById(@PathVariable("customerId") UUID customerId,
                                             @RequestBody CustomerDTO customer) {

        // Calling service impl for internal functionality
        if (customerService.updateCustomerById(customerId, customer).isEmpty()) {
            throw new NotFoundException();
        }


        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    // DELETE THE CUSTOMER
    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID id) {


        if (!customerService.deleteCustomerById(id)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    // LIST ALL THE CUSTOMERS
    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> listCostumers() {
        return customerService.listCustomer();
    }

    // Exception handler for Not found error
    // DELETED



    // GET CUSTOMER BY ID
    @GetMapping(CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId) {

        log.debug("Getting the customer by id -in the service !!");

        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

}
