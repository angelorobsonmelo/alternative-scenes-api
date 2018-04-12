package com.angelorobson.alternativescene.controllers;


import com.angelorobson.alternativescene.entities.City;
import com.angelorobson.alternativescene.services.CityService;
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

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    private static final String URL_BASE = "/cities";
    private static final Long ID_STATE  = 1L;

    @Test
    public void it_should_find_all_cities_by_state() throws Exception {
        BDDMockito.given(cityService.findAllByState(ID_STATE)).willReturn(Arrays.asList(new City(), new City()));

        mockMvc.perform(get(URL_BASE + "/state/" + ID_STATE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.errors").isEmpty());
    }

}
