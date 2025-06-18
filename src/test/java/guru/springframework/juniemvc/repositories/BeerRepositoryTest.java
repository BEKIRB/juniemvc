package guru.springframework.juniemvc.repositories;

import guru.springframework.juniemvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {
        Beer beer = Beer.builder()
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer savedBeer = beerRepository.save(beer);

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void testFindBeerById() {
        Beer beer = Beer.builder()
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer savedBeer = beerRepository.save(beer);
        Optional<Beer> foundBeer = beerRepository.findById(savedBeer.getId());

        assertThat(foundBeer).isPresent();
        assertThat(foundBeer.get().getBeerName()).isEqualTo("Test Beer");
    }

    @Test
    void testUpdateBeer() {
        Beer beer = Beer.builder()
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer savedBeer = beerRepository.save(beer);
        
        savedBeer.setBeerName("Updated Beer");
        Beer updatedBeer = beerRepository.save(savedBeer);

        assertThat(updatedBeer.getBeerName()).isEqualTo("Updated Beer");
    }

    @Test
    void testDeleteBeer() {
        Beer beer = Beer.builder()
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer savedBeer = beerRepository.save(beer);
        beerRepository.deleteById(savedBeer.getId());

        Optional<Beer> foundBeer = beerRepository.findById(savedBeer.getId());
        assertThat(foundBeer).isEmpty();
    }

    @Test
    void testFindAllBeers() {
        Beer beer1 = Beer.builder()
                .beerName("Test Beer 1")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .beerName("Test Beer 2")
                .beerStyle("Stout")
                .upc("654321")
                .price(new BigDecimal("14.99"))
                .quantityOnHand(200)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerRepository.save(beer1);
        beerRepository.save(beer2);

        List<Beer> beers = beerRepository.findAll();

        assertThat(beers).isNotEmpty();
        assertThat(beers.size()).isGreaterThanOrEqualTo(2);
    }
}