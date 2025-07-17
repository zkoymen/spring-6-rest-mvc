package guru.springframework.spring6restmvc.repositories;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;



    @Test
    void testSaveBeerNameTooLong() {

        // Check before it is sent to database
        assertThrows(ConstraintViolationException.class, () -> {
            // exceed 50 char constraint
            Beer testBeer = beerRepository.save(Beer.builder()
                    .beerName("Zeynep BeerASDASDASDASDABeerASDASDASDASDABeerASDASDASDASDABeerASDASDASDASDABeerASDASDASDASDABeerASDASDASDASDABeerASDASDASDASDABeerASDASDASDASDABeerASDASDASDASDABeerASDASDASDASDABeerASDASDASDASDABeerASDASDASDASDAv")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("12421454656")
                    .price(new BigDecimal(150.50))
                    .build());

            // running to quickly it skips
            // flush --> it immediately writes to the db
            beerRepository.flush();
        });

    }



    @Test
    void testSaveBeer() {


        // add other properties to not fail the test
        Beer testBeer = beerRepository.save(Beer.builder()
                        .beerName("Zeynep Beer")
                        .beerStyle(BeerStyle.PALE_ALE)
                        .upc("12421454656")
                        .price(new BigDecimal(150.50))
                .build());


        // running to quickly it skips
        // flush --> it immediately writes to the db
        beerRepository.flush();

        // Make sure it has an id
        assertThat(testBeer).isNotNull();
        assertThat(testBeer.getId()).isNotNull();
    }
}