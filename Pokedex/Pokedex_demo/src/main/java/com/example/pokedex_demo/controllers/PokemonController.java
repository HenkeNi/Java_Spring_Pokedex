package com.example.pokedex_demo.controllers;

import com.example.pokedex_demo.entities.Pokemon;
import com.example.pokedex_demo.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/v1/pokemon")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping
    public ResponseEntity<List<Pokemon>> findPokemon(@RequestParam(required = false) String name) {
        var pokemon = pokemonService.findAll(name);
        return ResponseEntity.ok(pokemon); // return with status code: ok (200?) - 404 if failed
    }


    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> findPokemonById(@PathVariable String id) {
        return ResponseEntity.ok(pokemonService.findById(id));
    }

    // TODO: FIX!
    @GetMapping(params = "height")
    public ResponseEntity<List<Pokemon>> findPokemonByHeight(@RequestParam(required = true) int height) {
        System.out.println("HEIGHT!!!!!!!");
        return ResponseEntity.ok(pokemonService.findByHeight(height));
    }






    //@GetMapping(params = "name&weight") - needed?
    public Pokemon findPokemonByNameAndWeight(@RequestParam(required = true) String name, @RequestParam(required = true) int weight) {
        return pokemonService.findByNameAndWeight(name, weight);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Pokemon> savePokemon(@RequestBody Pokemon pokemon) {
        var savedPokemon = pokemonService.save(pokemon);
        return ResponseEntity.created(URI.create("/api/v1/pokemon/" + savedPokemon.getId())).body(savedPokemon);
    }


    @PutMapping("/{id}")
    @Secured({"ROLE_EDITOR", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT) // since void method
    public void updatePokemon(@PathVariable String id, @RequestBody Pokemon pokemon) {
        pokemonService.update(id, pokemon);
    }


    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePokemon(@PathVariable String id) {
        pokemonService.delete(id);
    }





/*

    @GetMapping
    public List<Pokemon> findPokemonByNameContain(@RequestParam(required = false) String name) {
        return pokemonService.findByNameContains(name);
    }

    @GetMapping
    public Pokemon findPokemonByPokedexIndex(@RequestParam(required = false) int ndex) {
        return pokemonService.findByNdex(ndex);
    }

    @GetMapping
    public List<Pokemon> findPokemonByTypes(@RequestParam(required = false) String[] types) {
        return pokemonService.findByType(types);
    }

    @GetMapping
    public List<Pokemon> findPokemonByWeight(@RequestParam(required = false) int weight) {
        return pokemonService.findByWeight(weight);
    }

    @GetMapping
    public List<Pokemon> findPokemonByHeight(@RequestParam(required = false) int height) {
        return pokemonService.findByHeight(height);
    }
*/




}
