package com.example.pokedex_demo.dto;

// dto - Data Transfer Object

import com.example.pokedex_demo.entities.Type;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PokemonDto {

    //@JsonProperty("name") // TODO: BEHÖVS????
    private String name;
    //@JsonProperty("types")
    private List<Type> types;
    //@JsonProperty("height")
    private int height;
    //@JsonProperty("weight")
    private int weight;
    @JsonProperty("id")
    private int ndex; // National Pokedéx Number;
    private String test;

    public PokemonDto() { }

    public PokemonDto(String name, List<Type> types, int height, int weight, int ndex) {
        this.name = name;
        this.types = types;
        this.height = height;
        this.weight = weight;
        this.ndex = ndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getNdex() {
        return ndex;
    }

    public void setNdex(int ndex) {
        this.ndex = ndex;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
