package com.example.pokedex_demo.repositories;


import com.example.pokedex_demo.entities.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonRepository extends MongoRepository<Pokemon, String> {

    //Optional<Pokemon> findByName(String name);
    List<Pokemon> findByNameContains(String name);
    List<Pokemon> findByName(String name);

    Optional<Pokemon> findByNdex(int ndex);
    List<Pokemon> findByTypes(String[] types);


    List<Pokemon> findByWeight(int weight);
    List<Pokemon> findByHeight(int height);

    @Query("{$and: [{ 'name': /?0/}, {'weight': ?1 }]}")
    List<Pokemon> findByNameContainingAndWeight(String name, int weight);

    /*
    @Query("{$and: [{ 'name': /?0/}, {'height': ?1 }]}")
    List<Pokemon> findByNameContainingAndHeight(String name, int height);

    @Query("{$and: [{ 'name': /?0/}, {'weight': ?1 }, {'height': ?2}]}")
    List<Pokemon> findByNameContainingAndWeightAndHeight(String name, int weight, int height);*/

    @Query("{$and: [{ 'weight': /?0/}, {'height': ?1 }]}")
    List<Pokemon> findByWeightAndHeight(int weight, int height);

}
