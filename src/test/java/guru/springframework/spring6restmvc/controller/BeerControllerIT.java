package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static guru.springframework.spring6restmvc.controller.BeerController.BEER_PATH_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//Full context
@SpringBootTest
public class BeerControllerIT {


    // Test controller and JPA interaction
    @Autowired
    BeerController beerController;


    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


//    @Test
//    void testPatchBeerBadName() throws Exception {
//        Beer beer = beerRepository.findAll().get(1);
//
//        // Mimicking JSON request body, having partial data
//        Map<String, Object> beerMap = new HashMap<>();
//        beerMap.put("beerName", "New Name2123123123123Name2123123123123Name2123123123123Name2123123123123Name2123123123123Name2123123123123Name2123123123123Name2123123123123Name2123123123123Name2123123123123Name2123123123123");
//
//        // Using URI variables for a cleaner refactoring. It does positional binding automatically
//        mockMvc.perform(patch(BEER_PATH_ID, beer.getId())
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(beerMap)))
//                .andExpect(status().isBadRequest());
//
//
//    }




    @Test
    void testPatchByIdNotFound() {

        assertThrows(NotFoundException.class, () -> {
           beerController.patchBeerById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testPatchById() {
        Beer beer =beerRepository.findAll().get(0);
        BeerDTO dto = beerMapper.beerToBeerDto(beer);

        dto.setBeerName("Ali Baba Birasi");

        ResponseEntity responseEntity = beerController.patchBeerById(beer.getId(), dto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));
        Beer patchedBeer = beerRepository.findById(beer.getId()).get();


        // Meaningless , we don't know which property is sent via JSON to  be modified
        // There could be no beerName provided by the client whatsoever
        assertThat(patchedBeer.getBeerName()).isEqualTo(beer.getBeerName());
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteById() {

        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity responseEntity = beerController.deleteBeerById(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        assertThat(beerRepository.findById(beer.getId()).isEmpty());

    }

    @Test
    void testDeleteByIdNotFound() {

        assertThrows(NotFoundException.class, () -> {
           beerController.deleteBeerById(UUID.randomUUID());
        });
        
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateBeer() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = beerMapper.beerToBeerDto(beer);
        // Just changing manually

        dto.setId(null);
        dto.setVersion(null);
        final String beerName = "efes";
        dto.setBeerName(beerName);


        ResponseEntity responseEntity = beerController.updateById(beer.getId(), dto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);

    }

    // Altering the db --> inserting new object to db
    // Don't want to interrupt any other tests
    @Rollback
    @Transactional
    @Test
    void saveNewBeerTest() {

        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New Beer")
                .build();

        // passing the beerDTO to create a new beer.
        ResponseEntity responseEntity = beerController.handlePost(beerDTO);


        // checks that the HTTP status returned is 201 (Created), which indicates that the beer was successfully created
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        // extracts the UUID of the newly created beer from the location URI:
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");

        // Extracts and converts the UUID from the location URI, which is expected to be at the 4th index of the split path array.
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();

    }


    @Test
    void testBeerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetBeerById() {

        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = beerController.getBeerById(beer.getId());
        assertThat(dto).isNotNull();

    }

    @Test
    void testListBeers() {
        List<BeerDTO> dtos = beerController.listBeers();
        assertThat(dtos.size()).isEqualTo(3);

    }

    // IF error --> rollback
    // It is going to return the db to its original state
    // So we don't fail the other test
    @Rollback
    @Transactional
    @Test
    void testEmptyList() {

        beerRepository.deleteAll();

        List<BeerDTO> dtos = beerController.listBeers();
        assertThat(dtos.size()).isEqualTo(0);


    }
}