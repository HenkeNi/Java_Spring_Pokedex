package com.example.pokedex_demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.util.List;

public class Pokemon implements Serializable {

    private static final long serialVersionUID = 4940804061197317191L;

    @Id
    private String id;
    private String name;
    private List<Object> types;
    private int height;
    private int weight;
    @Indexed(unique = true)
    private int ndex; // National Pokedéx Number;


    public Pokemon() { }

    public Pokemon(String name, List<Object> types, int height, int weight, int ndex) {
        this.name = name;
        this.types = types;
        this.height = height;
        this.weight = weight;
        this.ndex = ndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getTypes() {
        return types;
    }

    public void setTypes(List<Object> types) {
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
}
