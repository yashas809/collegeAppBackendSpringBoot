package com.collegeManagement.app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assignment")
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class AssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AssignmentPK")
    private long assignmentPK;

    @Column(name = "SubjectFK")
    private long subjectFK;

    @Column(name = "USN")
    private String usn;

    @Column(name = "isSubmitted")
    private boolean isSubmitted;
}
