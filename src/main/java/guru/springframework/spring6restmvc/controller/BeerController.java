package guru.springframework.spring6restmvc.controller;


import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    // Initializing the service ??
    private final BeerService beerService;


    // CREATE A NEW BEER
    // Location header returned --
    @PostMapping
    public ResponseEntity handlePost(@RequestBody Beer beer) {

        // Create the entity with Service implementations
        Beer savedBeer = beerService.saveNewBeer(beer);

        // Location header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        // Return the status message
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    // LIST ALL THE BEERS
    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    // GET BEER BY ID
    // No need to specify full path
    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {

        log.debug("Get Beer by Id -in controller -Testing the devtools 12345");

        return beerService.getBeerById(beerId);
    }

}
