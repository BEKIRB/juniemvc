package guru.springframework.juniemvc.services;

import guru.springframework.juniemvc.entities.Beer;

import java.util.List;
import java.util.Optional;

public interface BeerService {
    
    /**
     * Get all beers
     * @return List of all beers
     */
    List<Beer> getAllBeers();
    
    /**
     * Get a beer by its ID
     * @param id the beer ID
     * @return Optional containing the beer if found
     */
    Optional<Beer> getBeerById(Integer id);
    
    /**
     * Save a beer
     * @param beer the beer to save
     * @return the saved beer
     */
    Beer saveBeer(Beer beer);
}