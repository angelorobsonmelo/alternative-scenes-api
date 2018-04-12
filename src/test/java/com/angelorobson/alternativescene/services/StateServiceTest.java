package com.angelorobson.alternativescene.services;


import com.angelorobson.alternativescene.repositories.StateRepository;
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
public class StateServiceTest {

    @MockBean
    private StateRepository stateRepository;

    @InjectMocks
    @Autowired
    private StateSerivice stateSerivice;

    @Test
    public void it_should_get_all_states() {
        stateSerivice.findAll();

        verify(stateRepository).findAll();
    }
}
