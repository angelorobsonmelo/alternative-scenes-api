package com.angelorobson.alternativescene.services;


import com.angelorobson.alternativescene.repositories.CityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CityServiceTest {

    @MockBean
    private CityRepository cityRepository;

    @InjectMocks
    @Autowired
    private CityService cityService;

    private static final Long STATE_ID = 1L;

    @Test
    public void it_should_get_all_cities_by_state() {
        cityService.findAllByState(STATE_ID);

        verify(cityRepository).findAllByStateId(STATE_ID);
    }

}
