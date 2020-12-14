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
    public List<Pokemon> findPokemon(String name, String weight) {

        /*List<Pokemon> pokemon;

        if (name != null || weight == null) {
            pokemon = pokemonRepository.findByName(name);
        }
        else if (name == null && weight != null) {
            try {
                int intWeight = Integer.parseInt(weight);
                pokemon = pokemonRepository.findByWeight(Integer.parseInt(weight));
            } catch (Exception e) {
                System.out.println(e);
            }
        }*/

        if (name != null && weight != null) {
            var pokemon = pokemonRepository.findByNameContainingAndWeight(name, Integer.parseInt(weight));
            for (Pokemon pokemon1 : pokemon) {
                System.out.println(pokemon1);
            }
            return pokemonRepository.findByNameContainingAndWeight(name, Integer.parseInt(weight));
        }


        System.out.println("BY NAME!!!!");
        var allPokemon = pokemonRepository.findByNameContains(name);
        //var allPokemon = pokemonRepository.findAll();

        //var allPokemon = pokemonRepository.findByNameContains(name);

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


    public Pokemon findByNdex(int ndex) {
        return pokemonRepository.findByNdex(ndex).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found by national pokedex number!"));
    }


    public List<Pokemon> findByNameAndWeight(String name, String weight) {
        System.out.println("NAME AND WIE");
        var pokemon = findByNameAndWeight(name, weight);

        if (pokemon.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found by name and weight!");
        }
        return pokemon;
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







    //@Cacheable(value = "pokemonCache", key = "#id")
    /*public Pokemon findById(String id) {
        return pokemonRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found"));
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





}
