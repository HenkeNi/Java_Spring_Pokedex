package com.example.pokedex_demo.repositories;


import com.example.pokedex_demo.entities.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonRepository extends MongoRepository<Pokemon, String> {

    // NOTE: It's possible to chain multiple, and using deleteBy

    // TODO: return in order: findByXStartingWithOrderByYDesc(string)

    Optional<Pokemon> findByName(String name);
    List<Pokemon> findByNameContains(String name); // findByNameContaining

    Optional<Pokemon> findByNdex(int ndex);
    List<Pokemon> findByTypes(String[] types);

    List<Pokemon> findByWeight(int weight);
    List<Pokemon> findByHeight(int height);

    // TODO: Fixa i service istället??? lägg ihop resultat från name och weight
    Optional<Pokemon> findByNameAndWeight(String name, int weight);
    List<Pokemon> findByWeightAndHeight(int weight, int height);


    List<Pokemon> findByNdexGreaterThan(int ndex);
    List<Pokemon> findByNdexGreaterThanEqual(int ndex);

    List<Pokemon> findByWeightGreaterThan(int weight);
    List<Pokemon> findByWeightGreaterThanEqual(int weight);

    List<Pokemon> findByHeightGreaterThan(int height);
    List<Pokemon> findByHeightGreaterThanEqual(int height);

    List<Pokemon> findByNdexLessThan(int ndex);
    List<Pokemon> findByNdexLessThanEqual(int ndex);

    List<Pokemon> findByWeightLessThan(int height);
    List<Pokemon> findByWeightLessThanEqual(int height);

    List<Pokemon> findByHeightLessThan(int height);
    List<Pokemon> findByHeightLessThanEqual(int height);

    List<Pokemon> findByNdexBetween(int from, int to);
    List<Pokemon> findByWeightBetween(int from, int to);
    List<Pokemon> findByHeightBetween(int from, int to);


}
