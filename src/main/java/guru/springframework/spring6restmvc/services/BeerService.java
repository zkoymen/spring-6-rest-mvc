package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Beer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BeerService {

    // Method to display beers
    List<Beer> listBeers();

    Optional<Beer> getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateBeerById(UUID beerId, Beer beer);

    void deleteBeerById(UUID id);

    void patchBeerById(UUID id, Beer beer);
}
