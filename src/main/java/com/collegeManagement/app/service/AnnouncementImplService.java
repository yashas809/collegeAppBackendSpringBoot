package com.collegeManagement.app.service;

import com.collegeManagement.app.dao.AnnouncementsDAO;
import com.collegeManagement.app.entity.AnnounementEntity;
import com.collegeManagement.app.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementImplService implements IAnnouncementService{

    @Autowired
    private AnnouncementRepository repository;

    @Override
    public AnnouncementsDAO create(AnnouncementsDAO request) {
        repository.save(AnnounementEntity.build(0L, request.getTextMessage()));
        return request;
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<AnnouncementsDAO> getAll() {
        List<AnnounementEntity> optionalAnnouncementList = repository.findAll();
        if(!optionalAnnouncementList.isEmpty())
        {
            List<AnnouncementsDAO> response = new ArrayList<>();
            for(AnnounementEntity entity: optionalAnnouncementList)
            {
                response.add(AnnouncementsDAO.build(entity.getAccouncementPK(), entity.getTextMessage()));
            }
            return response;
        }
        return null;
    }
}
