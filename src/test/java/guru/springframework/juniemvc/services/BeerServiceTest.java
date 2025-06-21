package guru.springframework.juniemvc.services;

import guru.springframework.juniemvc.entities.Beer;
import guru.springframework.juniemvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BeerServiceTest {

    @Mock
    private BeerRepository beerRepository;

    @InjectMocks
    private BeerServiceImpl beerService;

    private Beer testBeer;
    private List<Beer> testBeerList;

    @BeforeEach
    void setUp() {
        testBeer = Beer.builder()
                .id(1)
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer testBeer2 = Beer.builder()
                .id(2)
                .beerName("Another Beer")
                .beerStyle("Stout")
                .upc("654321")
                .price(new BigDecimal("14.99"))
                .quantityOnHand(200)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        testBeerList = Arrays.asList(testBeer, testBeer2);
    }

    @Test
    void testGetAllBeers() {
        // Given
        given(beerRepository.findAll()).willReturn(testBeerList);

        // When
        List<Beer> beers = beerService.getAllBeers();

        // Then
        assertThat(beers).isNotNull();
        assertThat(beers.size()).isEqualTo(2);
        verify(beerRepository).findAll();
    }

    @Test
    void testGetBeerById() {
        // Given
        given(beerRepository.findById(1)).willReturn(Optional.of(testBeer));

        // When
        Optional<Beer> beerOptional = beerService.getBeerById(1);

        // Then
        assertThat(beerOptional).isPresent();
        assertThat(beerOptional.get().getId()).isEqualTo(1);
        assertThat(beerOptional.get().getBeerName()).isEqualTo("Test Beer");
        verify(beerRepository).findById(1);
    }

    @Test
    void testGetBeerByIdNotFound() {
        // Given
        given(beerRepository.findById(999)).willReturn(Optional.empty());

        // When
        Optional<Beer> beerOptional = beerService.getBeerById(999);

        // Then
        assertThat(beerOptional).isEmpty();
        verify(beerRepository).findById(999);
    }

    @Test
    void testSaveBeer() {
        // Given
        Beer beerToSave = Beer.builder()
                .beerName("New Beer")
                .beerStyle("Lager")
                .upc("111222")
                .price(new BigDecimal("10.99"))
                .quantityOnHand(50)
                .build();

        Beer savedBeer = Beer.builder()
                .id(3)
                .beerName("New Beer")
                .beerStyle("Lager")
                .upc("111222")
                .price(new BigDecimal("10.99"))
                .quantityOnHand(50)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        given(beerRepository.save(any(Beer.class))).willReturn(savedBeer);

        // When
        Beer result = beerService.saveBeer(beerToSave);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(3);
        assertThat(result.getBeerName()).isEqualTo("New Beer");
        verify(beerRepository).save(any(Beer.class));
    }

    @Test
    void testUpdateBeer() {
        // Given
        Beer beerToUpdate = Beer.builder()
                .beerName("Updated Beer")
                .beerStyle("Updated Style")
                .upc("999888")
                .price(new BigDecimal("15.99"))
                .quantityOnHand(75)
                .build();

        Beer existingBeer = Beer.builder()
                .id(1)
                .beerName("Test Beer")
                .beerStyle("IPA")
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer updatedBeer = Beer.builder()
                .id(1)
                .beerName("Updated Beer")
                .beerStyle("Updated Style")
                .upc("999888")
                .price(new BigDecimal("15.99"))
                .quantityOnHand(75)
                .createdDate(existingBeer.getCreatedDate())
                .updateDate(LocalDateTime.now())
                .build();

        given(beerRepository.findById(1)).willReturn(Optional.of(existingBeer));
        given(beerRepository.save(any(Beer.class))).willReturn(updatedBeer);

        // When
        Optional<Beer> result = beerService.updateBeer(1, beerToUpdate);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1);
        assertThat(result.get().getBeerName()).isEqualTo("Updated Beer");
        assertThat(result.get().getBeerStyle()).isEqualTo("Updated Style");
        verify(beerRepository).findById(1);
        verify(beerRepository).save(any(Beer.class));
    }

    @Test
    void testUpdateBeerNotFound() {
        // Given
        Beer beerToUpdate = Beer.builder()
                .beerName("Updated Beer")
                .beerStyle("Updated Style")
                .upc("999888")
                .price(new BigDecimal("15.99"))
                .quantityOnHand(75)
                .build();

        given(beerRepository.findById(999)).willReturn(Optional.empty());

        // When
        Optional<Beer> result = beerService.updateBeer(999, beerToUpdate);

        // Then
        assertThat(result).isEmpty();
        verify(beerRepository).findById(999);
    }

    @Test
    void testDeleteBeer() {
        // Given
        given(beerRepository.existsById(1)).willReturn(true);

        // When
        boolean result = beerService.deleteBeer(1);

        // Then
        assertThat(result).isTrue();
        verify(beerRepository).existsById(1);
        verify(beerRepository).deleteById(1);
    }

    @Test
    void testDeleteBeerNotFound() {
        // Given
        given(beerRepository.existsById(999)).willReturn(false);

        // When
        boolean result = beerService.deleteBeer(999);

        // Then
        assertThat(result).isFalse();
        verify(beerRepository).existsById(999);
    }
}