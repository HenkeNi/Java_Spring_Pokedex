package com.example.pokedex_demo.services;

import com.example.pokedex_demo.dto.PokemonDto;
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


    //     public List<Pokemon> findAll(String name, string weight) {

    @Cacheable(value = "pokemonCache", key = "#name")
    public List<Pokemon> findAll(String name) {

        if (name == null || name == "") {
            System.out.println("NO STRING!!!!!!!");
            List<PokemonDto> p = pokemonConsumerService.getOriginalPokemonFromAPi();

            
        }


        var allPokemon = pokemonRepository.findAll();
        //var allPokemon = pokemonRepository.findByNameContains(name);

        allPokemon = allPokemon.stream()
                .filter(pokemon -> pokemon.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (allPokemon.isEmpty()) {
            var pokemonDto = pokemonConsumerService.searchByName(name);
            /// var pokemonDTO = pokemonConsumerService.searc(name, weight);

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



    /*@Cacheable(value = "pokemonCache", key = "#name")
    public List<Pokemon> findPokemonByName(String name, String type) {
        if (pokemonRepository.findAll().isEmpty()) {
            pokemonConsumerService.getAllPokemonsFromApi();
        }
        if (name == null && type == null){
            return pokemonRepository.findAll();
        }
        if (name != null && type != null){
            var listFromDb = this.pokemonInDBCheckWithNameAndType(name, type);
            if (!listFromDb.isEmpty()){
                return listFromDb;
            }
        }else if(name != null){
            var listFromDb = this.pokemonInDBCheckWithName(name);
            if (!listFromDb.isEmpty()){
                return listFromDb;
            }
        }

        var pokemonData = pokemonConsumerService.search(name, type);

        return (List<Pokemon>) pokemonData;
    }*/


    /*

    private List<Pokemon> pokemonInDBCheckWithNameAndType(String name, String type){
        var pokemonsListedInDB = pokemonRepository.findAll();
        pokemonsListedInDB = pokemonsListedInDB.stream()
                .filter(pokemon -> pokemon.getName().toLowerCase().contains(name))
               // .filter(pokemon -> pokemon.getTypes().stream().anyMatch(pokeType -> pokeType.getType().name.toLowerCase().contains(type)))
                .collect(Collectors.toList());
        if(pokemonsListedInDB.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No pokemon found by name: %s and type: %s", name, type));
        }
        return pokemonsListedInDB;

    }
     */

    /*
      private List<Pokemon> pokemonInDBCheckWithName(String name){
        var pokemonsListedInDB = pokemonRepository.findAll();
        pokemonsListedInDB = pokemonsListedInDB.stream()
                .filter(pokemon -> pokemon.getName().toLowerCase().contains(name))
                .collect(Collectors.toList());
        if(pokemonsListedInDB.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No pokemon found by name: %s", name));
        }
        return pokemonsListedInDB;
    }
     */





    //@Cacheable(value = "pokemonCache", key = "#id")
    public Pokemon findById(String id) {
        return pokemonRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found"));
    }


    // TODO: FIX ! THROW ERROR IF LIST IS empty
    public List<Pokemon> findByHeight(int height) {

        // TODO: Pokemon.findAll() sen sortera ut rätt höjd!!?? FIXA I CONsumer service!!!!

        // TODO: MPSTE MAN HÄMTA ALLA POKEMON OCH SEN SORETA DEM??
        // BEHÖVER MAN KUNNA HÄMTA POKEMON SOM INTE FINNS I DATABASEN???

        var matchingPokemon = pokemonRepository.findByHeight(height);
        matchingPokemon = matchingPokemon.stream().collect(Collectors.toList());

        if (matchingPokemon != null || !matchingPokemon.isEmpty()) {
            return matchingPokemon;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found");
        /*return pokemonRepository.findByHeight(height).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found"));*/
    }


    public Pokemon findByNameAndWeight(String name, int weight) {
        return pokemonRepository.findByNameContainsAndWeightGreaterThanEqual(name, weight).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found"));
        //return pokemonRepository.findByNameAndWeight(name, weight).orElseThrow(() ->
         //       new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found"));
    }



    @CachePut(value = "pokemonCache", key = "#result.id")
    public Pokemon save(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }


    // @CachePut(value = "pokemonCache", key = "id")
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
