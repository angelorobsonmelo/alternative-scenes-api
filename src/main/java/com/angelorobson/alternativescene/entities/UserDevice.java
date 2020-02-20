package com.angelorobson.alternativescene.entities;

import javax.persistence.*;

@Entity
public class UserDevice {


    private Long Id;
    private String deviceId;
    private UserApp userApp;

    public UserDevice() {
    }

    public UserDevice(Long userId, String deviceId) {
        this.userApp = new UserApp();
        userApp.setId(userId);
        this.deviceId = deviceId;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @ManyToOne
    public UserApp getUserApp() {
        return userApp;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }
}
