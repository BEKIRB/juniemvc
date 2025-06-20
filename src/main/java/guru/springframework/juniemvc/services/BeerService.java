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

    /**
     * Update a beer
     * @param id the beer ID
     * @param beer the updated beer data
     * @return Optional containing the updated beer if found and updated
     */
    Optional<Beer> updateBeer(Integer id, Beer beer);

    /**
     * Delete a beer by its ID
     * @param id the beer ID
     * @return true if the beer was deleted, false if it was not found
     */
    boolean deleteBeer(Integer id);
}
