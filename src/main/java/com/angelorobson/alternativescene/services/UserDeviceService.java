package com.angelorobson.alternativescene.services;

import com.angelorobson.alternativescene.dtos.UserDeviceSaveDto;
import com.angelorobson.alternativescene.entities.UserDevice;
import com.angelorobson.alternativescene.enums.ProfileEnum;
import org.omg.IOP.ProfileIdHelper;

import java.util.List;
import java.util.Optional;

public interface UserDeviceService {

    UserDevice save(UserDeviceSaveDto userDeviceSaveDto);

    List<UserDevice> findAllByUserProfile(ProfileEnum profileEnum);

    Optional<UserDevice> findByDeviceId(String deviceId);

}
