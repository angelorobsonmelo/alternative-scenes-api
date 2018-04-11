package com.angelorobson.alternativescene.controllers;

import com.angelorobson.alternativescene.dtos.UserAppDto;
import com.angelorobson.alternativescene.dtos.UserAppSaveDto;
import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.enums.ProfileEnum;
import com.angelorobson.alternativescene.services.UserAppService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.angelorobson.alternativescene.builders.GenericBuilder.of;
import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

  private Page<UserAppDto> pagedResponse;

  private UserAppDto userAppDto;

  private static final Long ID = 1L;

  @Before
  public void setUp() {
    List<UserAppDto> userAppsDto = getUserAppsDto();
    pagedResponse = new PageImpl(userAppsDto);
    userAppDto = new UserAppDto();
    userAppDto.setId(1L);
    userAppDto.setEmail("jose@gmail.com");
    userAppDto.setImageUrl("https://image_1.JPG");
    userAppDto.setName("José da silva");
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
    given(userAppService.findAll(any(PageRequest.class))).willReturn(pagedResponse);
    
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
    given(userAppService.persist(any(UserApp.class))).willReturn(userAppDto);

    String jsonRequisition = this.getJsonRequisitionPost(null,"josé", "jose@gmail.com", "123",
            "http://image_1.JPG", LocalDate.of(2010, 1, 3));

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data").isNotEmpty())
      .andExpect(jsonPath("$.errors").isEmpty());
  }

  @Test
  public void it_should_persist_user_with_name_null_value() throws Exception {
    given(userAppService.persist(any(UserApp.class))).willReturn(userAppDto);

    String jsonRequisition = this.getJsonRequisitionPost(null,null, "jose@gmail.com", "123",
            "http://image_1.JPG", LocalDate.of(2010, 1, 3));

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.data").isEmpty())
      .andExpect(jsonPath("$.errors").isNotEmpty())
      .andExpect(jsonPath("$.errors[0]").value("Name can not be empty."));
  }


  @Test
  public void it_should_persist_user_with_email_null_value() throws Exception {
    given(userAppService.persist(any(UserApp.class))).willReturn(userAppDto);

    String jsonRequisition = this.getJsonRequisitionPost(null,"José", null, "123",
            "http://image_1.JPG", LocalDate.of(2010, 1, 3));

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.data").isEmpty())
      .andExpect(jsonPath("$.errors").isNotEmpty())
      .andExpect(jsonPath("$.errors[0]").value("Email can not be empty."));
  }

  @Test
  public void it_should_persist_user_with_password_null_value() throws Exception {
    given(userAppService.persist(any(UserApp.class))).willReturn(userAppDto);

    String jsonRequisition = this.getJsonRequisitionPost(null,"José", "jose@gmail.com", null,
            "http://image_1.JPG", LocalDate.of(2010, 1, 3));

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.data").isEmpty())
      .andExpect(jsonPath("$.errors").isNotEmpty())
      .andExpect(jsonPath("$.errors[0]").value("Password can not be empty."));
  }

  @Test
  public void it_should_persist_user_with_image_null_value() throws Exception {
    given(userAppService.persist(any(UserApp.class))).willReturn(userAppDto);

    String jsonRequisition = this.getJsonRequisitionPost(null,"José", "jose@gmail.com", "123",
            null, LocalDate.of(2010, 1, 3));

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.data").isNotEmpty())
      .andExpect(jsonPath("$.errors").isEmpty());
  }

  @Test
  public void it_should_persist_user_with_datebirth_null_value() throws Exception {
    given(userAppService.persist(any(UserApp.class))).willReturn(userAppDto);

    String jsonRequisition = this.getJsonRequisitionPost(null,"José", "jose@gmail.com", "123",
            "http://image_1.JPG", null);

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.data").isEmpty())
      .andExpect(jsonPath("$.errors").isNotEmpty())
      .andExpect(jsonPath("$.errors[0]").value("DateBirth can not be empty."));
  }

  @Test
  public void it_should_persist_user_with_all_empty_required_fields() throws Exception {
    given(userAppService.persist(any(UserApp.class))).willReturn(userAppDto);

    String jsonRequisition = this.getJsonRequisitionPost(null,null, null, null,
            "http://image_1.JPG", null);

    mockMvc.perform(post(URL_BASE)
      .content(jsonRequisition)
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.data").isEmpty())
      .andExpect(jsonPath("$.errors").isNotEmpty())
      .andExpect(jsonPath("$.errors[0]").isNotEmpty())
      .andExpect(jsonPath("$.errors[1]").isNotEmpty())
      .andExpect(jsonPath("$.errors[2]").isNotEmpty())
      .andExpect(jsonPath("$.errors[3]").isNotEmpty());
  }

    @Test
    public void it_should_edit_user() throws Exception {
        UserApp userApp = new UserApp();
        userApp.setId(1L);
        userApp.setDateBirth(LocalDate.of(2010, 1, 3));
        userApp.setName("José");
        userApp.setEmail("emailquetemqueficar@ho.com");
        userApp.setPassword("senhaqudeveficar");
        userApp.setImageUrl("http://image_1.JPG");
        userApp.setProfile(ProfileEnum.ROLE_ADMIN);
        userApp.setRegistrationDate(LocalDate.now());

        given(userAppService.findById(anyLong())).willReturn(Optional.of(userApp));
        given(userAppService.edit(any(UserApp.class))).willReturn(userAppDto);

        String jsonRequisition = this.getJsonRequisitionPost(Optional.of(1L), "Manoel", "monoel@gmail.com", "manu123",
                "http://image_2.JPG", LocalDate.of(2010, 1, 10));

        mockMvc.perform(put(URL_BASE)
                .content(jsonRequisition)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.errors").isEmpty());
    }


    @Test
    public void it_should_return_user_not_found_when_editing_a_non_existent_user() throws Exception {
        given(userAppService.findById(anyLong())).willReturn(Optional.empty());
        given(userAppService.edit(any(UserApp.class))).willReturn(userAppDto);

        String jsonRequisition = this.getJsonRequisitionPost(Optional.of(1L), "Manoel", "monoel@gmail.com", "manu123",
                "http://image_2.JPG", LocalDate.of(2010, 1, 10));

        mockMvc.perform(put(URL_BASE)
                .content(jsonRequisition)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.errors[0]").value("User not found."));
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
    public void it_should_remove_user() throws Exception {
      given(this.userAppService.findById(anyLong())).willReturn(Optional.of(new UserApp()));

      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
    public void it_should_remove_non_existent_user() throws Exception {
      given(this.userAppService.findById(anyLong())).willReturn(Optional.empty());

      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Error removing user. Record not found for id " + ID));
    }

    @Test
    @WithMockUser
    public void it_should_access_denied_when_removing_a_user() throws Exception {
      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isForbidden());
    }

    @Test
    public void it_should_access_denied_when_removing_a_user_without_logged() throws Exception {
      mockMvc.perform(delete(URL_BASE + "/" +ID))
                .andExpect(status().isUnauthorized());
    }

  private String getJsonRequisitionPost(Optional<Long> id, String name, String email, String password, String imageUrl, LocalDate birthDate) throws JsonProcessingException {
    UserAppSaveDto userAppSaveDto = new UserAppSaveDto();
    userAppSaveDto.setId(id);
    userAppSaveDto.setName(name);
    userAppSaveDto.setEmail(email);
    userAppSaveDto.setPassword(password);
    userAppSaveDto.setImageUrl(imageUrl);
    userAppSaveDto.setDateBirth(birthDate);
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new Jdk8Module());
    return mapper.writeValueAsString(userAppSaveDto);
  }

}
