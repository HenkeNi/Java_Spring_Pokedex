package com.example.pokedex_demo.controllers;

import org.junit.jupiter.api.Test;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@ExtendWith(RestDocumentationExtension.class)
//@AutoConfigureRestDocs
class PokemonControllerTest {

    @Test
    public void doNothing() {

    }

    /*
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

        Type type = new Type("Fight", "some.url");

        return new Pokemon("MagraMon", List.of(type), 201, 2000, 9999);
    }*/
}
