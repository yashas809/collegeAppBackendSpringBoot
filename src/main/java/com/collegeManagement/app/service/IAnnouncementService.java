package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.AnnouncementsDAO;

import java.util.List;

public interface IAnnouncementService
{
    public AnnouncementsDAO create(AnnouncementsDAO request);

    public void delete(long id);

    public List<AnnouncementsDAO> getAll();
}
