package com.angelorobson.alternativescene.controllers;

import com.angelorobson.alternativescene.dtos.UserAppDto;
import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.services.UserAppService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.angelorobson.alternativescene.builders.GenericBuilder.of;
import static java.util.Arrays.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserAppControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserAppService userAppService;

  private static final String URL_BASE = "/users_app";

  @Before
  public void setUp() {
    List<UserAppDto> userAppsDto = getUserAppsDto();
    Page<UserAppDto> pagedResponse = new PageImpl(userAppsDto);

    when(userAppService.findAll(any(PageRequest.class))).thenReturn(pagedResponse);
  }

  private List<UserAppDto> getUserAppsDto() {
    UserAppDto userAppOne = of(UserAppDto::new)
      .with(UserAppDto::setId, 1L)
      .with(UserAppDto::setName, "josé da silva")
      .with(UserAppDto::setImageUrl, "https://image_1.jpg")
      .build();

    UserAppDto userAppTwo = of(UserAppDto::new)
      .with(UserAppDto::setId, 2L)
      .with(UserAppDto::setName, "João Pereira")
      .with(UserAppDto::setImageUrl, "https://image_2.jpg")
      .build();

    return asList(userAppOne, userAppTwo);
  }

  @Test
  public void it_shold_return_users() throws Exception {
    mockMvc.perform(get(URL_BASE))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data.content[0].id").value("1"))
      .andExpect(jsonPath("$.data.content[0].name").value("josé da silva"))
      .andExpect(jsonPath("$.data.content[1].id").value("2"))
      .andExpect(jsonPath("$.data.content[1].name").value("João Pereira"))
      .andExpect(jsonPath("$.errors").isEmpty());
  }

}
