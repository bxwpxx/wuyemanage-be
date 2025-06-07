package com.example.demo.service;

import com.example.demo.config.DatabaseConnectionPool;
import com.example.demo.dao.AnnouncementDAO;
import com.example.demo.dao.OwnerDAO;
import com.example.demo.dao.PropertyDAO;
import com.example.demo.dao.UserDAO;
import com.example.demo.domain.entity.Announcement;
import com.example.demo.domain.entity.Property;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.vo.AnnouncementItem;
import com.example.demo.domain.vo.CreateAnnouncementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Service
public class AnnouncementServiceImpI implements AnnouncementService{
    private final AnnouncementDAO announcementDAO;
    private final PropertyDAO propertyDAO;
    private final UserDAO userDAO;
    @Autowired
    public AnnouncementServiceImpI(AnnouncementDAO announcementDAO, PropertyDAO propertyDAO,UserDAO userDAO) {
        this.announcementDAO = announcementDAO;
        this.propertyDAO=propertyDAO;
        this.userDAO=userDAO;
    }

    @Override
    public boolean createAnnouncement(CreateAnnouncementRequest createAnnouncementRequest) {
        try {
            Announcement announcement=new Announcement();
            announcement.setTitle(createAnnouncementRequest.getTitle());
            announcement.setContent(createAnnouncementRequest.getContent());
            announcement.setUploaderId(createAnnouncementRequest.getUploaderId());
            announcementDAO.insertAnnouncement(announcement);
            return true; // 插入成功返回true
        } catch (SQLException e) {
            return false; // 插入失败返回false
        }
    }

    @Override
    public boolean deleteAnnouncement(Integer announcementId) {
        try {
            announcementDAO.deleteAnnouncement(announcementId);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Announcement getAnnouncementById(Integer announcementId) {
        try {
            Announcement announcement=announcementDAO.getAnnouncementById(announcementId);
            int loaderId=announcement.getUploaderId();
            User user=userDAO.getUserById(propertyDAO.getPropertyByPropertyID(loaderId).getUserID());
            announcement.setUploaderName(user.getUsername());
            return announcement;
        } catch (SQLException e) {
            return null;
        }

    }

    @Override
    public List<AnnouncementItem> getAllAnnouncements() {
        try {
            List<Announcement> announcements=announcementDAO.getAllAnnouncements();
            List<AnnouncementItem> announcementItems=new ArrayList<>();
            for (Announcement announcement : announcements)
            {
                int loaderId=announcement.getUploaderId();
                User user=userDAO.getUserById(propertyDAO.getPropertyByPropertyID(loaderId).getUserID());
                announcement.setUploaderName(user.getUsername());
                announcement.setContent(null);
                announcementItems.add(new AnnouncementItem(announcement));
            }
            return announcementItems;
        } catch (SQLException e) {
            return null;
        }
    }
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        PropertyDAO propertyDAO=null;
        UserDAO userDAO =null;
        AnnouncementDAO announcementDAO=null;
        try {
            // 从连接池获取连接
            connection = DatabaseConnectionPool.getConnection();

            // 创建 DAO 实例
            userDAO = new UserDAO();
            propertyDAO=new PropertyDAO();
            announcementDAO=new AnnouncementDAO();

        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
        }
        AnnouncementServiceImpI announcementServiceImpI=new AnnouncementServiceImpI(announcementDAO,propertyDAO,userDAO);
        for (AnnouncementItem announcement:announcementServiceImpI.getAllAnnouncements())
        {
            System.out.println( announcement);
        }
        System.out.println(announcementServiceImpI.getAnnouncementById(3));

        // 关闭连接
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("关闭连接失败: " + e.getMessage());
            }
        }
        // 关闭连接池
        DatabaseConnectionPool.closePool();
    }


}
