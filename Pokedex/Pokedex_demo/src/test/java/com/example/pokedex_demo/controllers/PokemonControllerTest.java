package com.example.pokedex_demo.controllers;

import com.example.pokedex_demo.entities.Pokemon;
import com.example.pokedex_demo.repositories.PokemonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
class PokemonControllerTest {

    //@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PokemonRepository pokemonRepository;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void findPokemon() throws Exception {
        given(pokemonRepository.findAll()).willReturn(List.of(retrieveTestPokemon()));

        mockMvc.perform(get("/api/v1/pokemon")
                .param("name", "Bulbasaur")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/pokemon-get-all",
                        requestParameters(parameterWithName("name").description("The pokemon name to search for"))
                ));
    }

    @Test
    void findPokemonById() throws Exception {
        given(pokemonRepository.findById(any())).willReturn(Optional.of(retrieveTestPokemon()));

        mockMvc.perform(get("/api/v1/pokemon/{id}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/pokemon/get-one",
                        pathParameters(parameterWithName("id").description("The id for the requested pokemon")),
                        responseFields(
                                fieldWithPath("id").description("Pokemon ID"),
                                fieldWithPath("name").description("Pokemon Name"),
                                fieldWithPath("types").description("Pokemon Types"),
                                fieldWithPath("height").description("Pokemon Height"),
                                fieldWithPath("weight").description("Pokemon Weight"),
                                fieldWithPath("ndex").description("National Pokedex Index")
                        )));
    }

    @Test
    void findPokemonByHeight() {
    }

    @Test
    void findPokemonByNameAndWeight() {
    }

    @Test
    void savePokemon() throws Exception {
        var pokemon = retrieveTestPokemon();
        pokemon.setId(null);
        var pokemonJson = objectMapper.writeValueAsString(pokemon);

        given(pokemonRepository.save(any())).willReturn(retrieveTestPokemon());

        mockMvc.perform(post("/api/v1/pokemon")
                .content(pokemonJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("v1/pokemon-new",
                        requestFields(
                                fieldWithPath("id").ignored(),
                                fieldWithPath("name").description("Pokemon Name"),
                                fieldWithPath("types").description("Pokemon Types"),
                                fieldWithPath("height").description("Pokemon Height"),
                                fieldWithPath("weight").description("Pokemon Weight"),
                                fieldWithPath("ndex").description("National Pokedex Index")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Pokemon ID"),
                                fieldWithPath("name").description("Pokemon Name"),
                                fieldWithPath("types").description("Pokemon Types"),
                                fieldWithPath("height").description("Pokemon Height"),
                                fieldWithPath("weight").description("Pokemon Weight"),
                                fieldWithPath("ndex").description("Nation Pokedex Index")
                        )));
    }

    @Test
    void updatePokemon() throws Exception {
        var pokemonJson = objectMapper.writeValueAsString(retrieveTestPokemon());

        given(pokemonRepository.existsById(any())).willReturn(true);

        mockMvc.perform(put("/api/v1/pokemon/{id}", UUID.randomUUID().toString())
                .content(pokemonJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("v1/pokemon-update",
                        pathParameters(parameterWithName("id").description("The id for the requested pokemon")),
                        requestFields(
                                fieldWithPath("id").description("Pokemon ID"),
                                fieldWithPath("name").description("Pokemon Name"),
                                fieldWithPath("types").description("Pokemon Types"),
                                fieldWithPath("height").description("Pokemon Height"),
                                fieldWithPath("weight").description("Pokemon Weight"),
                                fieldWithPath("ndex").description("National Pokedex Index")
                        )));
    }


    @Test
    void deletePokemon() throws Exception {
        given(pokemonRepository.existsById(any())).willReturn(true);
        mockMvc.perform(delete("/api/v1/pokemon/{id}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("v1/pokemon-delete",
                        pathParameters(parameterWithName("id").description("The id for the requested pokemon"))
                ));
    }

    private Pokemon retrieveTestPokemon() {

        return new Pokemon("MagraMon", List.of("Mega", "Cool"), 201, 2000, 9999);

        /*return Pokemon.builder()
                .name("Magra")
                .types(List.of("Mega", "Cool"))
                .height(201)
                .weight(2000)
                .ndex(99999)
                .build();*/
    }
}