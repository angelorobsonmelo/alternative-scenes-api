package com.angelorobson.alternativescene.controllers;

import com.angelorobson.alternativescene.dtos.UserAppDto;
import com.angelorobson.alternativescene.dtos.UserAppSaveDto;
import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.UserAppService;
import com.angelorobson.alternativescene.utils.PasswordUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.angelorobson.alternativescene.builders.GenericBuilder.of;
import static com.angelorobson.alternativescene.utils.PasswordUtils.*;
import static java.time.Month.JANUARY;
import static java.util.Arrays.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

  @Test
  public void it_should_persist_user() throws Exception {
    UserAppDto userAppDto = new UserAppDto();
    userAppDto.setId(1L);
    userAppDto.setEmail("jose@gmail.com");
    userAppDto.setImageUrl("https://image_1.JPG");
    userAppDto.setName("José da silva");

    given(userAppService.persist(any(UserApp.class))).willReturn(userAppDto);

    String jsonRequisition = this.getJsonRequisitionPost();
    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data").isNotEmpty())
      .andExpect(jsonPath("$.errors").isEmpty());
  }

  private String getJsonRequisitionPost() throws JsonProcessingException {
    UserAppSaveDto userAppSaveDto = new UserAppSaveDto();
    userAppSaveDto.setName("José");
    userAppSaveDto.setEmail("jose@gmail.com");
    userAppSaveDto.setPassword("123");
    userAppSaveDto.setImageUrl("https://img_1.JPG");
    userAppSaveDto.setDateBirth(LocalDate.of(2014, 1, 1));
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(userAppSaveDto);
  }

}
