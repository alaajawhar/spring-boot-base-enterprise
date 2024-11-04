package com.amdose.base.repositories.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Alaa Jawhar
 */
@Getter
@Setter
@Entity
@Table(name = "USER_STORY")
public class UserStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // HERE GOES YOUR TABLE'S COLUMNS

    // THE BELOW SHOULD BE ADDED FOR EVERY TABLE YOU CREATE FOR AUDITING
    @Column(name = "ADDED_BY")
    private Long addedBy;

    @Column(name = "ADDED_DATE")
    private Date addedDate;

    @Column(name = "MODIFIED_BY")
    private Long modifiedBy;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;
}
