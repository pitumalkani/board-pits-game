package com.game.bol.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldInitGame() throws Exception {

        MockHttpServletRequestBuilder initGameRequest = MockMvcRequestBuilders.post(BASE_PATH+"/init?numberOfStones=6");
         mockMvc.perform(initGameRequest)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                //check game state
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ACTIVE"))

                //check total pit size
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.size()", Matchers.is(14)))

                //check pit index
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.1.pitIndex").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.8.pitIndex").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.14.pitIndex").value(14));

    }
    @Test
    public void shouldInitGameShouldThrowBadRequestException() throws Exception {

        MockHttpServletRequestBuilder initGameRequest = MockMvcRequestBuilders.post(BASE_PATH+"/init");
        mockMvc.perform(initGameRequest)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}
