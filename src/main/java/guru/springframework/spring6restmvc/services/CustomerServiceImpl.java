package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> customerMap;

    // Examples initialized
    public CustomerServiceImpl() {

        // Map first
        this.customerMap = new HashMap<>();

        Customer cst1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Jones Elenor")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer cst2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Mordechai Rigby")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        Customer cst3 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Steve Jobs")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        customerMap.put(cst1.getId(), cst1);
        customerMap.put(cst2.getId(), cst2);
        customerMap.put(cst3.getId(), cst3);


    }

    @Override
    public List<Customer> listCustomer() {
        // return created ones
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {

        log.debug("Getting the customer by id! -in the service");
        return customerMap.get(id);
    }

    @Override
    public Customer saveNewCustomer(Customer customer) {

        // 1- Create the object in JPA
        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName(customer.getCustomerName())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        // 2- Add to HasMap
        customerMap.put(savedCustomer.getId(), savedCustomer);

        // 3- Return the saved object
        return savedCustomer;
    }

    @Override
    public void updateCustomerById(UUID customerId, Customer customer) {

        Customer existing = customerMap.get(customerId);

        // OR you can use PATCH request to partially update w/o having to specify everything
        if (customer.getCustomerName() != null) existing.setCustomerName(customer.getCustomerName());
        if (customer.getLastModifiedDate() != null) existing.setLastModifiedDate(customer.getLastModifiedDate());
        if (customer.getCreatedDate() != null) existing.setCreatedDate(customer.getLastModifiedDate());



    }

    @Override
    public void deleteCustomerById(UUID id) {

        customerMap.remove(id);
    }

    @Override
    public void patchCustomerById(UUID id, Customer customer) {


        Customer existing = customerMap.get(id);

        if (StringUtils.hasText(customer.getCustomerName())) existing.setCustomerName(customer.getCustomerName());
        if (customer.getLastModifiedDate() != null) existing.setLastModifiedDate(customer.getLastModifiedDate());
        if (customer.getCreatedDate() != null) existing.setCreatedDate(customer.getLastModifiedDate());
        if (customer.getLastModifiedDate() != null) existing.setLastModifiedDate(customer.getLastModifiedDate());
    }

}
