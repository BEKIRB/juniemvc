package guru.springframework.juniemvc.services;

import guru.springframework.juniemvc.entities.Beer;

import java.util.List;
import java.util.Optional;

public interface BeerService {
    List<Beer> listBeers();
    Optional<Beer> getBeerById(Integer id);
    Beer saveBeer(Beer beer);
}