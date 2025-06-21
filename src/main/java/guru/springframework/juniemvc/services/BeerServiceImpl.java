package guru.springframework.juniemvc.services;

import guru.springframework.juniemvc.entities.Beer;
import guru.springframework.juniemvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    /**
     * Retrieves all beers from the database
     * @return a list of all beers
     */
    @Override
    public List<Beer> getAllBeers() {
        return beerRepository.findAll();
    }

    /**
     * Retrieves a beer by its ID
     * @param id the ID of the beer to retrieve
     * @return an Optional containing the beer if found, or empty if not found
     */
    @Override
    public Optional<Beer> getBeerById(Integer id) {
        return beerRepository.findById(id);
    }

    /**
     * Saves a new beer to the database
     * @param beer the beer object to save
     * @return the saved beer with generated ID
     */
    @Override
    public Beer saveBeer(Beer beer) {
        return beerRepository.save(beer);
    }

    /**
     * Updates an existing beer
     * @param id the ID of the beer to update
     * @param beer the updated beer data
     * @return an Optional containing the updated beer if found and updated, or empty if not found
     */
    @Override
    public Optional<Beer> updateBeer(Integer id, Beer beer) {
        return beerRepository.findById(id)
                .map(existingBeer -> {
                    beer.setId(id);
                    beer.setVersion(existingBeer.getVersion());
                    beer.setCreatedDate(existingBeer.getCreatedDate());
                    return beerRepository.save(beer);
                });
    }

    /**
     * Deletes a beer by its ID
     * @param id the ID of the beer to delete
     * @return true if the beer was found and deleted, false if it was not found
     */
    @Override
    public boolean deleteBeer(Integer id) {
        if (beerRepository.existsById(id)) {
            beerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
