package com.example.pokedex_demo.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.util.List;

@ApiModel(description = "Pokemon Model")
public class Pokemon implements Serializable {

    private static final long serialVersionUID = 4940804061197317191L;

    @ApiModelProperty(notes = "Id of the Pokemon", name = "id", required = true, value = "1")
    @Id
    private String id;
    @ApiModelProperty(notes = "Name of the Pokemon", name = "name", required = true, value = "Bulbasaur")
    private String name;
    @ApiModelProperty(notes = "Types of the Pokemon", name = "types", required = true, value = "Grass")
    private List<Type> types;
    @ApiModelProperty(notes = "Height of the Pokemon", name = "height", required = true, value = "13")
    private int height;
    @ApiModelProperty(notes = "Weight of the Pokemon", name = "weight", required = true, value = "200")
    private int weight;
    @ApiModelProperty(notes = "Pokedex Number of the Pokemon", name = "ndex", required = true, value = "01")
    @Indexed(unique = true)
    private int ndex; // National Pokedéx Number;


    public Pokemon() { }

    public Pokemon(String name, List<Type> types, int height, int weight, int ndex) {
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
}
