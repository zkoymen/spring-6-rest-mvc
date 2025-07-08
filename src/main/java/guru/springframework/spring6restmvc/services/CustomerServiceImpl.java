package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    // Examples initialized
    public CustomerServiceImpl() {

        // Map first
        this.customerMap = new HashMap<>();

        CustomerDTO cst1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Jones Elenor")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO cst2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Mordechai Rigby")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        CustomerDTO cst3 = CustomerDTO.builder()
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
    public List<CustomerDTO> listCustomer() {
        // return created ones
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {

        log.debug("Getting the customer by id! -in the service");
        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {

        // 1- Create the object in JPA
        CustomerDTO savedCustomer = CustomerDTO.builder()
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
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {

        CustomerDTO existing = customerMap.get(customerId);

        // OR you can use PATCH request to partially update w/o having to specify everything
        if (customer.getCustomerName() != null) existing.setCustomerName(customer.getCustomerName());

        // Do not put it in the map instead return optional object
        return Optional.of(existing);


    }

    @Override
    public Boolean deleteCustomerById(UUID id) {

        customerMap.remove(id);
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID id, CustomerDTO customer) {


        CustomerDTO existing = customerMap.get(id);

        if (StringUtils.hasText(customer.getCustomerName())) existing.setCustomerName(customer.getCustomerName());
        if (customer.getLastModifiedDate() != null) existing.setLastModifiedDate(customer.getLastModifiedDate());
        if (customer.getCreatedDate() != null) existing.setCreatedDate(customer.getLastModifiedDate());
        if (customer.getLastModifiedDate() != null) existing.setLastModifiedDate(customer.getLastModifiedDate());

        return Optional.of(existing);
    }



}
