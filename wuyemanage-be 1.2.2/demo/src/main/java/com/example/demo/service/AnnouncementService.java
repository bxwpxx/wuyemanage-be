package com.example.demo.service;

import com.example.demo.domain.entity.Announcement;
import com.example.demo.domain.entity.Property;
import com.example.demo.domain.vo.AnnouncementItem;
import com.example.demo.domain.vo.CreateAnnouncementRequest;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * 公告业务服务接口
 */
@Service
public interface AnnouncementService {

    /**
     * 创建新公告
     * @param announcement 公告实体（无需设置ID）
     * @return 包含生成ID的公告实体
     */
    boolean createAnnouncement(CreateAnnouncementRequest createAnnouncementRequest) throws SQLException;


    /**
     * 删除公告
     * @param announcementId 公告ID
     * @return 是否删除成功
     */
    boolean deleteAnnouncement(Integer announcementId);

    /**
     * 获取公告详情
     * @param announcementId 公告ID
     * @return 公告实体
     */
    Announcement getAnnouncementById(Integer announcementId);

    /**
     * 获取所有公告列表（按时间倒序）
     * @return 公告列表
     */
    List<AnnouncementItem> getAllAnnouncements();


}