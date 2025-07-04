package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, BeerDTO> beerMap;

    //Constructor initializing 3 instance
    public BeerServiceImpl() {
        this.beerMap =new HashMap<>();



        // Generating a POJO to return back
        // Using Builder design pattern
        BeerDTO beer1 = BeerDTO.builder().
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

        BeerDTO beer2 = BeerDTO.builder()
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



        BeerDTO beer3 = BeerDTO.builder()
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
    public List<BeerDTO> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {

        log.debug("Getting the Beer by Id at the moment -in the service");

        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {

        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .price(beer.getPrice())
                .upc(beer.getUpc())
                .quantityOnHand(beer.getQuantityOnHand())
                .build();


        beerMap.put(savedBeer.getId(), savedBeer);

        return savedBeer;
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beer) {

        BeerDTO existing = beerMap.get(beerId);

        existing.setBeerName(beer.getBeerName());
        existing.setBeerStyle(beer.getBeerStyle());
        existing.setPrice(beer.getPrice());
        existing.setUpc(beer.getUpc());
        existing.setQuantityOnHand(beer.getQuantityOnHand());

        beerMap.put(existing.getId(), existing);

    }

    @Override
    public void deleteBeerById(UUID id) {

        beerMap.remove(id);


    }

    @Override
    public void patchBeerById(UUID id, BeerDTO beer) {


        BeerDTO existing = beerMap.get(id);

        if (StringUtils.hasText(beer.getBeerName()))  existing.setBeerName(beer.getBeerName());
        if (beer.getBeerStyle() != null) existing.setBeerStyle(beer.getBeerStyle());
        if (beer.getPrice() != null) existing.setPrice(beer.getPrice());
        if (StringUtils.hasText(beer.getUpc())) existing.setUpc(beer.getUpc());
        if (beer.getQuantityOnHand()!= null) existing.setQuantityOnHand(beer.getQuantityOnHand());

        beerMap.put(existing.getId(), existing);



    }
}
