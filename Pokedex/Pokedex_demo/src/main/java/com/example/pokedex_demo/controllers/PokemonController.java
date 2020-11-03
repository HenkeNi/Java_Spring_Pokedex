package com.example.pokedex_demo.controllers;

import com.example.pokedex_demo.entities.Pokemon;
import com.example.pokedex_demo.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


//@RequestMapping("/rest/v1/pokemon")
//@RequestMapping("https://pokeapi.co/api/v2/pokemon/")
@RestController
@RequestMapping("/api/v1/pokemon")
public class PokemonController {

    // dependency

    @Autowired
    private PokemonService pokemonService;


    // ResponseEntity (sends back responds to client) - with status code
    @GetMapping // TODO: rename findAllPokemon???
    public ResponseEntity<List<Pokemon>> findPokemon(@RequestParam(required = false) String name) { // TODO: Make param optional (required false)
        var pokemon = pokemonService.findAll(name);
        return ResponseEntity.ok(pokemon); // return with status code: ok (200?) - 404 if failed
    }


    @GetMapping("/{id}")
    public ResponseEntity<Pokemon>  findPokemonById(@PathVariable String id) {
        return ResponseEntity.ok(pokemonService.findById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pokemon> savePokemon(@RequestBody Pokemon pokemon) {
        var savedPokemon = pokemonService.save(pokemon);
        return ResponseEntity.created(URI.create("/api/v1/pokemon/" + savedPokemon.getId())).body(savedPokemon);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // since void method
    public void updatePokemon(@PathVariable String id, @RequestBody Pokemon pokemon) {
        pokemonService.update(id, pokemon);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        pokemonService.delete(id);
    }



/*
    @Autowired
    private PokemonService pokemonService;

    @GetMapping("/{id}")
    public Pokemon findPokemonById(@PathVariable String id) {
        return pokemonService.findById(id);
    }

    //public ResponseEntity<List<Pokemon>> findAllPokemon(@RequestParam String name)
    @GetMapping
    public List<Pokemon> findAllPokemon(@RequestParam(required = false) String name)
    {
        return pokemonService.findAll(name);
    }


    // TODO Add findBYname

    // TODO: BARA HA EN GETMAPPING???

    // WHEN TO USE @REQUEST PARAM AND PathVariable????
    //TEST
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


    // "/rest/v1/pokemon?name=bulbasaur&weight=11"
    @GetMapping
    public Pokemon findPokemonByNameAndWeight(@RequestParam(required = false) String name, @RequestParam(required = false) int weight) {
        return pokemonService.findByNameAndWeight(name, weight);
    }

    @GetMapping
    public List<Pokemon> findPokemonByWeightAndHeight(@RequestParam(required = false) int weight, @RequestParam(required = false) int height) {
        return pokemonService.findByWeightAndHeight(weight, height);
    }


   @PostMapping
   public Pokemon savePokemon(@RequestBody Pokemon pokemon) {
        return pokemonService.save(pokemon);
   }


   @PutMapping("/{id")
   public void updatePokemon(@PathVariable String id, @RequestBody Pokemon pokemon) {
        pokemonService.update(id, pokemon);
   }


   @DeleteMapping("/{id}")
   public void deletePokemon(@PathVariable String id) {
        pokemonService.delete(id);
   }

*/



    /*

    C - @PostMapping
    R - @GetMapping
    U - @PutMapping
    D - @DeleteMapping

     */
}
