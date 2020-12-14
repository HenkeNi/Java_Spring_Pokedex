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


    public List<PokemonDto> getOriginalPokemonFromAPi() {
        var urlWithOriginalQuery = url + "?limit=151&offset=0";
        var orignalPokemon = restTemplate.getForObject(urlWithOriginalQuery, PokemonDto.class);

        if (orignalPokemon == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found!");
        }

        return List.of(orignalPokemon);
    }



    // DELTE?
    void some() {
        restTemplate.put(url, new PokemonDto()); // spara?
        restTemplate.postForEntity(url, new PokemonDto(), PokemonDto.class); // updatera??
        restTemplate.delete(url);
    }


}
