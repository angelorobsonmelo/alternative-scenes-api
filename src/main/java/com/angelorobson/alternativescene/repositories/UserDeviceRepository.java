package com.angelorobson.alternativescene.repositories;

import com.angelorobson.alternativescene.entities.UserApp;
import com.angelorobson.alternativescene.entities.UserDevice;
import com.angelorobson.alternativescene.enums.ProfileEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    Optional<UserDevice> findByUserApp(UserApp userApp);

    List<UserDevice> findAllByUserApp_Profile(ProfileEnum profileEnum);

    Optional<UserDevice> findByDeviceId(String deviceId);
}
