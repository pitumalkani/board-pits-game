package com.game.bol.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.bol.model.Game;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class GameControllerTest {
    private static final String BASE_PATH = "/api/game";
    private static final Integer INITIAL_STONE_ON_PIT = 6;
    private static final Integer INITIAL_STONE_ON_HOUSE = 0;
    private static final Integer PLAYER1_INDEX = 1;
    private static final Integer PLAYER2_INDEX = 2;
    private static MockHttpServletRequestBuilder initGameRequest;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeAll

    public static void setup() {
        initGameRequest = MockMvcRequestBuilders.post(BASE_PATH + "/init?numberOfStones=6");

    }

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
     void shouldInitGame() throws Exception {
        mockMvc.perform(initGameRequest).andExpect(MockMvcResultMatchers.status().isCreated())
                //check game state
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ACTIVE"))

                //check total pit size
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.size()", Matchers.is(14)))

                //check pit index
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.1.pitIndex").value(1)).andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.8.pitIndex").value(8)).andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.14.pitIndex").value(14));

    }

    @Test
     void shouldPlayGame() throws Exception {
        String responseString = mockMvc.perform(initGameRequest).andReturn().getResponse().getContentAsString();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Game game = objectMapper.readValue(responseString, Game.class);

        initGameRequest = MockMvcRequestBuilders.post(BASE_PATH + "/" + game.getId() + "/move?pitIndex=1");

        mockMvc.perform(initGameRequest).andExpect(MockMvcResultMatchers.status().isOk())

                //check game id
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(game.getId()))

                //check total pit size
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.size()", Matchers.is(14)))

                //check player index
                .andExpect(MockMvcResultMatchers.jsonPath("$.player1.playerIndex").value(PLAYER1_INDEX)).andExpect(MockMvcResultMatchers.jsonPath("$.player2.playerIndex").value(PLAYER2_INDEX))


                //starting pit should be zero
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.1.stoneCount").value(0))

                //pit should increment by 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.2.stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.3.stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.4.stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.5.stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.6.stoneCount").value(7))

                //player 1 house should increment by 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.7.stoneCount").value(1))

                //check game state as end with player 1 house
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("PLAYER1_TURN"))

                .andReturn();


    }

    @Test
    void shouldPlayGameForPlayer2() throws Exception {
        String responseString = mockMvc.perform(initGameRequest).andReturn().getResponse().getContentAsString();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Game game = objectMapper.readValue(responseString, Game.class);
        initGameRequest = MockMvcRequestBuilders.post(BASE_PATH + "/" + game.getId() + "/move?pitIndex=8");
        mockMvc.perform(initGameRequest).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("PLAYER2_TURN"));
    }
}
