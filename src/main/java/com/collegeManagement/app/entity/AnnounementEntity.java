package com.collegeManagement.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "anouncement")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Data
public class AnnounementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccouncementPK")
    private long AccouncementPK;

    @Column(name = "TextMessage")
    private String textMessage;
}
