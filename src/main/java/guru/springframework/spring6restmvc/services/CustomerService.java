package guru.springframework.spring6restmvc.services;


import guru.springframework.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<Customer> listCustomer();

    Optional<Customer> getCustomerById(UUID id);

    Customer saveNewCustomer(Customer costumer);

    void updateCustomerById(UUID customerId, Customer customer);

    void deleteCustomerById(UUID id);

    void patchCustomerById(UUID id, Customer customer);
}
