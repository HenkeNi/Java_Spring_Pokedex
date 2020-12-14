package com.example.pokedex_demo.controllers;

import com.example.pokedex_demo.entities.Pokemon;
import com.example.pokedex_demo.services.PokemonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@ApiOperation(value = "/api/v1/pokemon", tags = "Pokemon Controller")
@RestController
@RequestMapping("/api/v1/pokemon")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;



    /*@GetMapping
    public ResponseEntity<List<Pokemon>> findPokemon(@RequestParam(required = false) String name) {
        var pokemon = pokemonService.findAll(name);
        return ResponseEntity.ok(pokemon); // return with status code: ok (200?) - 404 if failed
    }*/


    // pokemon?name=_NAME_&weight=_10_
    @ApiOperation(value = "Fetch all Pokemon", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED!"),
            @ApiResponse(code = 403, message = "FORBIDDEN!"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping
    public ResponseEntity<List<Pokemon>> findPokemon(@RequestParam(required = false) String name,
                                                     @RequestParam(required = false) String weight) {
        var pokemon = pokemonService.findPokemon(name, weight);

        if (pokemon.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(pokemon); // return with status code: ok (200?) - 404 if failed
    }


    // pokemon/1
    @ApiOperation(value = "Get Pokemon by pokedex number", response = Pokemon.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED!"),
            @ApiResponse(code = 403, message = "FORBIDDEN!"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> findPokemonByNdex(@PathVariable int id) {
        return ResponseEntity.ok(pokemonService.findByNdex(id));
    }


    /*@GetMapping("/{id}")
    public ResponseEntity<Pokemon> findPokemonById(@PathVariable String id) {
        return ResponseEntity.ok(pokemonService.findById(id));
    }*/


    @ApiOperation(value = "Save Pokemon", response = Pokemon.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED!"),
            @ApiResponse(code = 403, message = "FORBIDDEN!"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Pokemon> savePokemon(@RequestBody Pokemon pokemon) {
        var savedPokemon = pokemonService.save(pokemon);
        return ResponseEntity.created(URI.create("/api/v1/pokemon/" + savedPokemon.getId())).body(savedPokemon);
    }


    @ApiOperation(value = "Update Pokemon details")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED!"),
            @ApiResponse(code = 403, message = "FORBIDDEN!"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @PutMapping("/{id}")
    @Secured({"ROLE_EDITOR", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.NO_CONTENT) // since void method
    public void updatePokemon(@PathVariable String id, @RequestBody Pokemon pokemon) {
        pokemonService.update(id, pokemon);
    }


    @ApiOperation(value = "Delete Pokemon" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED!"),
            @ApiResponse(code = 403, message = "FORBIDDEN!"),
            @ApiResponse(code = 404, message = "NOT FOUND")
    })
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePokemon(@PathVariable String id) {
        pokemonService.delete(id);
    }

}
