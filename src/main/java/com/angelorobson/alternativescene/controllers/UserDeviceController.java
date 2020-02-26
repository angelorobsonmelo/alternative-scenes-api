package com.angelorobson.alternativescene.controllers;

import com.angelorobson.alternativescene.dtos.UserDeviceSaveDto;
import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.entities.EventDate;
import com.angelorobson.alternativescene.entities.UserDevice;
import com.angelorobson.alternativescene.entities.notification.Sender;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.UserDeviceService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/userDevices")
public class UserDeviceController {

    private UserDeviceService service;

    @Autowired
    public UserDeviceController(UserDeviceService userDeviceService) {
        this.service = userDeviceService;
    }

    @PostMapping
    public ResponseEntity<Response<Boolean>> save(@RequestBody UserDeviceSaveDto dto) {
        Response<Boolean> response = new Response<>();

        Optional<UserDevice> userDeviceReturned = service.findByDeviceId(dto.getDeviceId());

        if (userDeviceReturned.isPresent()) {
            response.setData(true);
            return ResponseEntity.ok(response);
        }

        UserDevice userDevice = service.save(dto);

        if (userDevice.getId() != null) {
            return ResponseEntity.ok(response);
        }

        response.setData(false);
        return ResponseEntity.ok(response);
    }

}
