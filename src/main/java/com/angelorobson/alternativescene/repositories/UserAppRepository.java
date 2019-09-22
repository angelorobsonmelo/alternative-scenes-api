package com.angelorobson.alternativescene.repositories;


import com.angelorobson.alternativescene.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserAppRepository extends JpaRepository<UserApp, Long> {

    UserApp findByEmail(String email);
    UserApp findByEmailAndAndGoogleAccountId(String email, String googleAccountId);
}
