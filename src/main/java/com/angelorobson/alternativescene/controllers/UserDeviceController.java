package com.angelorobson.alternativescene.controllers;

import com.angelorobson.alternativescene.dtos.UserDeviceSaveDto;
import com.angelorobson.alternativescene.entities.UserDevice;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        UserDevice userDevice = service.save(dto);

        if (userDevice.getId() != null) {
            response.setData(true);
            return ResponseEntity.ok(response);
        }

        response.setData(false);
        return ResponseEntity.ok(response);
    }

}
