package guru.springframework.juniemvc.controllers;

import guru.springframework.juniemvc.entities.Beer;
import guru.springframework.juniemvc.services.BeerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<Beer> getBeerById(@PathVariable("beerId") Integer beerId) {
        Optional<Beer> beerOptional = beerService.getBeerById(beerId);
        
        return beerOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Beer createBeer(@RequestBody Beer beer) {
        return beerService.saveBeer(beer);
    }
}