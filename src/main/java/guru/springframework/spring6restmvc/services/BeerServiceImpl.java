package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap;

    //Constructor initializing 3 instance
    public BeerServiceImpl() {
        this.beerMap =new HashMap<>();



        // Generating a POJO to return back
        // Using Builder design pattern
        Beer beer1 = Beer.builder().
                id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("1245")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.ALE)
                .upc("14234")
                .price(new BigDecimal("15.66"))
                .quantityOnHand(456)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();



        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("98989")
                .price(new BigDecimal("6.89"))
                .quantityOnHand(55)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();


        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    // Method to display beers
    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer getBeerById(UUID id) {

        log.debug("Getting the Beer by Id at the moment -in the service");

        return beerMap.get(id);
    }
}
