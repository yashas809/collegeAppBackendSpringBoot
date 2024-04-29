package com.collegeManagement.app.dao;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class AnnouncementsDAO
{
    private long id;
    private String textMessage;
}
