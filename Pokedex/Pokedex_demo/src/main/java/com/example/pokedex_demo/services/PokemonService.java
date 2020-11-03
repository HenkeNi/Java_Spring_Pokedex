package com.example.pokedex_demo.services;

import com.example.pokedex_demo.entities.Pokemon;
import com.example.pokedex_demo.repositories.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonService {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private PokemonConsumerService pokemonConsumerService;


    @Cacheable(value = "pokemonCache", key = "#name")
    public List<Pokemon> findAll(String name) {

        System.out.println("Not from cache (db or fetching)");

        //return pokemonRepository.findAll();
        var allPokemon = pokemonRepository.findAll();
        allPokemon = allPokemon.stream()
                .filter(pokemon -> pokemon.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (allPokemon.isEmpty()) {
            var pokemonDto = pokemonConsumerService.search(name);

            if (pokemonDto != null) {
                var pokemon = new Pokemon(
                        pokemonDto.getName(),
                        pokemonDto.getTypes(),
                        pokemonDto.getHeight(),
                        pokemonDto.getWeight(),
                        pokemonDto.getNdex());

                allPokemon.add(save(pokemon));
            }
        }
        return allPokemon;
    }


    public Pokemon findById(String id) {
        return pokemonRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found"));
    }


    // CachePut - updates data?
    @CachePut(value = "pokemonCache", key = "#result.id")
    //@CacheEvict(value = "pokemonCache", allEntries = true)
    public Pokemon save(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }


    @CachePut(value = "pokemonCache", key = "#id")
    public void update(String id, Pokemon pokemon) {
        if (!pokemonRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found");
        }
        pokemon.setId(id);
        pokemonRepository.save(pokemon);
    }


    @CacheEvict(value = "pokemonCache", allEntries = true)
    public void delete(String id) {
        if (!pokemonRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found");
        }
        pokemonRepository.deleteById(id);
    }



    // Crud metoder - hämta, spara, etc


}
