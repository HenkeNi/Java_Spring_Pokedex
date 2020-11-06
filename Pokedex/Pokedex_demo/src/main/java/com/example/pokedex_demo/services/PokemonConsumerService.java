package com.example.pokedex_demo.services;


import com.example.pokedex_demo.dto.PokemonDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@ConfigurationProperties(value = "example.pokemon", ignoreUnknownFields = false)
public class PokemonConsumerService {

    private final RestTemplate restTemplate;
    private String url;

    public PokemonConsumerService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public PokemonDto search(String name) {
        var urlWithNameQuery = url + "pokemon/" + name;

        var pokemon = restTemplate.getForObject(urlWithNameQuery, PokemonDto.class);

        if (pokemon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found!");
        }
        return pokemon;
    }


    /*public PokemonDto searchByHeight(int height) {
        var urlWithHeightQuery = url + "pokemon/" + height;
    }*/


    public List<PokemonDto> getOriginalPokemonFromAPi() {
        var urlWithOriginalQuery = url + "?limit=151&offset=0";
        var orignalPokemon = restTemplate.getForObject(urlWithOriginalQuery, PokemonDto.class);

        if (orignalPokemon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found!");
        }

        return List.of(orignalPokemon);
        //orignalPokemon.getResult().forEach(pokemon -> PokeApiHarvestDto.save(new PokeApiHarvest(pokemon.getName(), pokemon.getType(), pokemon.getHeight()
       // ,pokemon.getWeight(), pokemon.getNdex())));
    }



    // DELTE?
    void some() {
        restTemplate.put(url, new PokemonDto()); // spara?
        restTemplate.postForEntity(url, new PokemonDto(), PokemonDto.class); // updatera??
        restTemplate.delete(url);
    }








    /*@Autowired
    private PokemonRepository pokemonRepository;


    public List<Pokemon> findAll(String name) {
        if (name != null) {
            var pokemon = pokemonRepository.findByName(name).orElseThrow(RuntimeException::new);
            return List.of(pokemon);
        }
        return pokemonRepository.findAll();
    }


    public Pokemon findById(String id) {
        return pokemonRepository.findById(id).orElseThrow(RuntimeException::new);
    }


    public Pokemon findByPokemonName(String name) {
        return pokemonRepository.findByPokemonName(name).orElseThrow(RuntimeException::new);
    }


    public Pokemon save(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }


    public void update(String id, Pokemon pokemon) {

        if (!pokemonRepository.existsById(id)) {
            throw new RuntimeException();
        }
        pokemon.setId(id);
        pokemonRepository.save(pokemon);
    }


    public void delete(String id) {

        if (!pokemonRepository.existsById(id)) {
            throw new RuntimeException();
        }
        pokemonRepository.deleteById(id);
    }


    // TEST
    public List<Pokemon> findByNameContains(String name) {
        return pokemonRepository.findByNameContains(name);
    }

    public Pokemon findByNdex(int ndex) {
        return pokemonRepository.findByNdex(ndex).orElseThrow(RuntimeException::new);
    }

    public List<Pokemon> findByType(String[] types) {
        return pokemonRepository.findByTypes(types);
    }

    public List<Pokemon> findByWeightAndHeight(int weight, int height) {
        return pokemonRepository.findByWeightAndHeight(weight, height);
    }

    public Pokemon findByNameAndWeight(String name, int weight) {
        return pokemonRepository.findByNameAndWeight(name, weight).orElseThrow(RuntimeException::new);
    }


    public List<Pokemon> findByWeight(int weight) {
        return pokemonRepository.findByWeight(weight);
    }

    public List<Pokemon> findByHeight(int height) {
        return pokemonRepository.findByHeight(height);
    }*/
}
