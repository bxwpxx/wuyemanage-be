package com.example.demo.controller;

import com.example.demo.domain.entity.Announcement;
import com.example.demo.domain.entity.Owner;
import com.example.demo.domain.entity.Property;
import com.example.demo.domain.vo.CreateAnnouncementRequest;
import com.example.demo.domain.vo.LoginRequest;
import com.example.demo.domain.vo.LoginResponse;
import com.example.demo.service.AnnouncementService;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin
@RestController(value = "as")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }
    @PostMapping("/getAllAnnouncements")
    public List<Announcement> getAllAnnouncements() throws SQLException {
        return announcementService.getAllAnnouncements();
    }
    @PostMapping("/getAnnouncementById")
    public Announcement getAnnouncementById(@RequestBody int credentials) throws SQLException {
        return announcementService.getAnnouncementById(credentials);
    }
    @PostMapping("/getAnnouncementById")
    public boolean deleteAnnouncement(@RequestBody int credentials) throws SQLException {
         return  announcementService.deleteAnnouncement(credentials);
    }
    @PostMapping("/createAnnouncement")
    public boolean createAnnouncement(@RequestBody CreateAnnouncementRequest createAnnouncementRequest) throws SQLException {
        return  announcementService.createAnnouncement(createAnnouncementRequest);
    }
  /*
    @PostMapping("/OwnerSign")
    public String OwnerSign(@RequestBody Owner credentials) throws SQLException {
        return authService.authorizeOwner(credentials);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest credentials) throws SQLException {
        return authService.authenticate(credentials.getUserID(), credentials.getPassword());
    }
   */

}