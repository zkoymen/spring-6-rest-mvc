package guru.springframework.spring6restmvc.controller;


import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
// Not this
// @AllArgsConstructor
@RequiredArgsConstructor
@RestController
@RequestMapping
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    // Initializing the service ??
    private final BeerService beerService;


    // UPDATE THE BEER
    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {

        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    // Check every property before setting it
    // PATCH THE BEER :)
    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity patchBeerById(@PathVariable("beerId") UUID id, @RequestBody Beer beer) {

        beerService.patchBeerById(id, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }




    // DELETE THE BEER
    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteBeerById(@PathVariable("beerId") UUID id) {

        beerService.deleteBeerById(id);

        return  new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    // CREATE A NEW BEER
    // Location header returned --
    @PostMapping(BEER_PATH)
    public ResponseEntity handlePost(@RequestBody Beer beer) {

        // Create the entity with Service implementations
        Beer savedBeer = beerService.saveNewBeer(beer);

        // Location header --> Constructing a URI
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        // Return the status message
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    // LIST ALL THE BEERS
    @GetMapping(BEER_PATH)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }



    // provide custom exception class
    // *** DELETED IT




    // GET BEER BY ID
    // No need to specify full path
    @GetMapping(BEER_PATH_ID)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {

        log.debug("Get Beer by Id -in controller -Testing the devtools 12345");

        return beerService.getBeerById(beerId);
    }

}
