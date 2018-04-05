package com.angelorobson.alternativescene.repositories;

import com.angelorobson.alternativescene.builders.GenericBuilder;
import com.angelorobson.alternativescene.dtos.UserAppDto;
import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.enums.ProfileEnum;
import com.angelorobson.alternativescene.utils.PasswordUtils;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestParam;

import static com.angelorobson.alternativescene.builders.GenericBuilder.of;
import static com.angelorobson.alternativescene.utils.PasswordUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.springframework.data.domain.Sort.Direction.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserAppRepositoryTest {

  @Autowired
  UserAppRepository userAppRepository;

  @Before
  public void setUp() {
    UserApp userApp = of(UserApp::new)
      .with(UserApp::setId, 1L)
      .with(UserApp::setName, "josé da silva")
      .with(UserApp::setImageUrl, "https://image_1.jpg")
      .with(UserApp::setProfile, ProfileEnum.ROLE_USER)
      .with(UserApp::setPassword, generateBCrypt("1234"))
      .with(UserApp::setEmail, "jose@gmail.com")
      .build();

    this.userAppRepository.save(userApp);
  }

  @After
  public final void tearDown() {
    this.userAppRepository.deleteAll();
  }

  @Test
  public void it_should_find_user_by_email() {
    UserApp userApp = this.userAppRepository.findByEmail("jose@gmail.com");

    assertThat(userApp.getEmail(), is(equalTo("jose@gmail.com")));
  }

  @Test
  public void it_should_find_users() {
    Page<UserApp> userAppPage = this.userAppRepository.findAll(new PageRequest(0, 25, valueOf("DESC"),
      "id"));

    assertThat(userAppPage.getContent().size(), is(equalTo(1)));
  }


}
