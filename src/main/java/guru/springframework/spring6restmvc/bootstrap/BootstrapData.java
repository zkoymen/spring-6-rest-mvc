package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BootstrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final BeerRepository beerRepository;

    public BootstrapData(BeerRepository beerRepository ,CustomerRepository customerRepository) {
        this.beerRepository = beerRepository;
        this.customerRepository = customerRepository;

    }
    @Override
    public void run(String... args) throws Exception {

        loadBeerData();
        loadCustomerData();
    }

    private void loadCustomerData() {

        if (customerRepository.count() == 0){
            Customer cst1 = Customer.builder()
                    .customerName("Jones Elenor")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer cst2 = Customer.builder()
                    .customerName("Mordechai Rigby")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Customer cst3 = Customer.builder()
                    .customerName("Steve Jobs")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();


            // Save them all
            customerRepository.save(cst1);
            customerRepository.save(cst2);
            customerRepository.save(cst3);
        }
    }

    private void loadBeerData() {

        if ( beerRepository.count() == 0){
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("1245")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle(BeerStyle.ALE)
                    .upc("14234")
                    .price(new BigDecimal("15.66"))
                    .quantityOnHand(456)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();


            Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle(BeerStyle.IPA)
                    .upc("98989")
                    .price(new BigDecimal("6.89"))
                    .quantityOnHand(55)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();


            // Save these
            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }

    }


}
