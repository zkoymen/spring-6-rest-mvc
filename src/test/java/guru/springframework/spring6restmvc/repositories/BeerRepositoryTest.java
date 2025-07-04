package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {

        Beer testBeer = beerRepository.save(Beer.builder()
                        .beerName("Zeynep Beer")
                .build());

        // Make sure it has an id
        assertThat(testBeer).isNotNull();
        assertThat(testBeer.getId()).isNotNull();
    }
}