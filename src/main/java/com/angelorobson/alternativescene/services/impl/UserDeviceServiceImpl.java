package com.angelorobson.alternativescene.services.impl;

import com.angelorobson.alternativescene.converters.Converters;
import com.angelorobson.alternativescene.dtos.UserDeviceSaveDto;
import com.angelorobson.alternativescene.entities.UserDevice;
import com.angelorobson.alternativescene.enums.ProfileEnum;
import com.angelorobson.alternativescene.repositories.UserDeviceRepository;
import com.angelorobson.alternativescene.services.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDeviceServiceImpl implements UserDeviceService {

    private UserDeviceRepository repository;

    @Autowired
    public UserDeviceServiceImpl(UserDeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDevice save(UserDeviceSaveDto userDeviceSaveDto) {
        UserDevice userDevice = Converters.convertDtoToEntity(userDeviceSaveDto);
        return repository.save(userDevice);
    }

    @Override
    public List<UserDevice> findAllByUserProfile(ProfileEnum profileEnum) {
        return repository.findAllByUserApp_Profile(profileEnum);
    }

    @Override
    public Optional<UserDevice> findByDeviceId(String deviceId) {
        return repository.findByDeviceId(deviceId);
    }
}
