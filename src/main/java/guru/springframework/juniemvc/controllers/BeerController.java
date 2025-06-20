package guru.springframework.juniemvc.controllers;

import guru.springframework.juniemvc.entities.Beer;
import guru.springframework.juniemvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/beers")
@RequiredArgsConstructor
public class BeerController {

    private final BeerService beerService;

    @GetMapping
    public ResponseEntity<List<Beer>> getAllBeers() {
        return new ResponseEntity<>(beerService.getAllBeers(), HttpStatus.OK);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<Beer> getBeerById(@PathVariable("beerId") Integer beerId) {
        Optional<Beer> beerOptional = beerService.getBeerById(beerId);
        
        return beerOptional
                .map(beer -> new ResponseEntity<>(beer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Beer> createBeer(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveBeer(beer);
        return new ResponseEntity<>(savedBeer, HttpStatus.CREATED);
    }
}