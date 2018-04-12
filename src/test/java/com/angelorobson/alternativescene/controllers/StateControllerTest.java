package com.angelorobson.alternativescene.controllers;

import com.angelorobson.alternativescene.entities.State;
import com.angelorobson.alternativescene.services.StateSerivice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StateSerivice stateSerivice;

    private static final String URL_BASE = "/states";

    private List<State> states;

    @Before
    public void setUp() {
        states = Arrays.asList(new State(), new State());
    }

    @Test
    public void it_should_get_all_states() throws Exception {
        given(stateSerivice.findAll()).willReturn(states);

        mockMvc.perform(get(URL_BASE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

}
