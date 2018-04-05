package com.angelorobson.alternativescene.services;

import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.repositories.UserAppRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserAppServiceTest {

  @MockBean
  private UserAppRepository userAppRepository;

  @InjectMocks
  @Autowired
  private UserAppService userAppService;

  private UserApp userApp;

  private static final String EMAIL = "admin@gmail.com";
  private static final Long ID = 1L;

  @Before
  public void setUp() {
    userApp = new UserApp();
    when(userAppRepository.save(any(UserApp.class))).thenReturn(new UserApp());
    when(userAppRepository.findByEmail(anyString())).thenReturn(new UserApp());
    when(userAppRepository.findOne(anyLong())).thenReturn(new UserApp());
  }

  @Test
  public void it_should_persist_user() {
    UserApp userAppReturned = userAppService.persist(this.userApp);

    verify(userAppRepository).save(eq(this.userApp));
    assertNotNull(userAppReturned);
  }

  @Test
  public void it_should_find_by_email() {
    Optional<UserApp> userAppReturned = userAppService.findByEmail(EMAIL);

    verify(userAppRepository).findByEmail(eq(EMAIL));
    assertTrue(userAppReturned.isPresent());
  }

  @Test
  public void it_should_find_by_id() {
    Optional<UserApp> userAppReturned = userAppService.findById(ID);

    verify(userAppRepository).findOne(eq(ID));
    assertTrue(userAppReturned.isPresent());

  }


}
