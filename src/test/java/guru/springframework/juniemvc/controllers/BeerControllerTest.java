package guru.springframework.juniemvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.juniemvc.entities.Beer;
import guru.springframework.juniemvc.services.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BeerControllerTest {

    MockMvc mockMvc;

    @Mock
    BeerService beerService;

    BeerController beerController;

    ObjectMapper objectMapper = new ObjectMapper();

    Beer validBeer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        beerController = new BeerController(beerService);
        mockMvc = MockMvcBuilders.standaloneSetup(beerController).build();

        validBeer = Beer.builder()
                .id(1)
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456789")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .build();
    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(Arrays.asList(validBeer));

        mockMvc.perform(get("/api/v1/beers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].beerName", is("Test Beer")));
    }

    @Test
    void testGetBeerById() throws Exception {
        given(beerService.getBeerById(1)).willReturn(Optional.of(validBeer));

        mockMvc.perform(get("/api/v1/beers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.beerName", is("Test Beer")));
    }

    @Test
    void testGetBeerByIdNotFound() throws Exception {
        given(beerService.getBeerById(1)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/beers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateBeer() throws Exception {
        Beer beerToSave = Beer.builder()
                .beerName("New Beer")
                .beerStyle("Lager")
                .upc("987654321")
                .price(new BigDecimal("9.99"))
                .quantityOnHand(50)
                .build();

        Beer savedBeer = Beer.builder()
                .id(2)
                .beerName("New Beer")
                .beerStyle("Lager")
                .upc("987654321")
                .price(new BigDecimal("9.99"))
                .quantityOnHand(50)
                .build();

        given(beerService.saveBeer(any(Beer.class))).willReturn(savedBeer);

        mockMvc.perform(post("/api/v1/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerToSave)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.beerName", is("New Beer")));
    }
}
