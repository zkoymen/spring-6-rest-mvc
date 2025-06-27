package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
