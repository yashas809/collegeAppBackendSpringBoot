package com.collegeManagement.app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "internalmarks")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor(staticName = "build")
public class InternalMarksEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InternalMarksPK")
    private long internalMarksPK;

    @Column(name = "USN")
    private String usn;

    @Column(name = "SubjectFK")
    private long subjectFK;

    @Column(name = "MaximumMarks")
    private int maximumMarks;

    @Column(name = "MarksScored")
    private double marksScored;
}
