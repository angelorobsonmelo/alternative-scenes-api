package com.angelorobson.alternativescene.controllers;

import com.angelorobson.alternativescene.dtos.UserDeviceSaveDto;
import com.angelorobson.alternativescene.entities.Event;
import com.angelorobson.alternativescene.entities.EventDate;
import com.angelorobson.alternativescene.entities.UserDevice;
import com.angelorobson.alternativescene.entities.notification.Notification;
import com.angelorobson.alternativescene.entities.notification.Sender;
import com.angelorobson.alternativescene.response.Response;
import com.angelorobson.alternativescene.services.UserDeviceService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    @PostMapping(value = "teste")
    public void teste() {
        HttpHeaders headers = new HttpHeaders();
        Sender<Event> eventSender = new Sender<>();
        Notification notification = new Notification();
        Event event = new Event();
        EventDate eventDate = new EventDate();

        String authorization = "key={0}";
        String headerAuthorization = MessageFormat.format(authorization,
                "AAAAsimAgCE:APA91bG7upq7kCkpXPeGzUOsvOVhEYCTflrHOxdhrrwVK3LeK1BdD2sQe4_pzasURelxWP6OLrZSMhualXjG_ZtEsdTr84-2gH1JaGoqmtspalw8wrLumcasyyJFf_YhFaok3dp33Gu7");

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", headerAuthorization);

        event.setImageThumbUrl("https://70000tons.com/wp-content/uploads/2017/05/70kt2018_originalstamp.jpg");

        eventDate.setDate(LocalDate.now());
        List<EventDate> dates = Arrays.asList(
                eventDate
        );
        event.setEventDates(dates);

        notification.setTitle("Novo evento adicionado");
        notification.setBody("tudo certo");
        notification.setImage(event.getImageThumbUrl());
        notification.setIcon(event.getImageThumbUrl());

        eventSender.setTo("cG5DMI4GUD0:APA91bFpCQNxrLPVbw1RWQsIMC2dgQwspL08WakvU1PQCp0MXykSFjMg8LSqM2hEIwtXwpgcp0YtkZbDQaw-cxpYH-u5tQMrtp6_rGwFH3HukOdX3bzhxnYQ4LuyIwhcMGNiXZGrKJ8N");
        eventSender.setNotification(notification);
        eventSender.setData(event);

        String Bodycontent = new Gson().toJson(eventSender);

        HttpEntity<String> requestEntity
                = new HttpEntity<>(Bodycontent, headers);

        String serverUrl = "https://fcm.googleapis.com/fcm/send";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(serverUrl, requestEntity, String.class);
    }

}
