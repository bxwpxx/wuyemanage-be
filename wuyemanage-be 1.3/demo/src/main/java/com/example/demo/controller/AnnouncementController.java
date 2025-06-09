package com.example.demo.controller;

import com.example.demo.domain.entity.Announcement;
import com.example.demo.domain.entity.Owner;
import com.example.demo.domain.entity.Property;
import com.example.demo.domain.vo.*;
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
@RestController
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }
    @PostMapping("/getAnnouncementList")
    public List<Announcement> getAllAnnouncements() throws SQLException {
        return announcementService.getAllAnnouncements();
    }
    @PostMapping("/getAnnouncementById")
    public Announcement getAnnouncementById(@RequestBody int credentials) throws SQLException {
        return announcementService.getAnnouncementById(credentials);
    }
    @PostMapping("/deleteAnnouncement")
    public boolean deleteAnnouncement(@RequestBody DeleteRequest credentials) throws SQLException {
         return  announcementService.deleteAnnouncement(credentials.getId());
    }
    @PostMapping("/createAnnouncement")
    public boolean createAnnouncement(@RequestBody CreateAnnouncementRequest createAnnouncementRequest) throws SQLException {
        return  announcementService.createAnnouncement(createAnnouncementRequest);
    }
}