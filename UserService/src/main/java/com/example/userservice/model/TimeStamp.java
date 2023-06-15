package com.example.userservice.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;

import java.time.LocalDateTime;

@MappedSuperclass
public class TimeStamp {
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;

    @PrePersist  //method annotated with this will be executed before data is saved in db
    public void createdDate() {
        this.createdDate = LocalDateTime.now(); //set the value of createdDate to LocalDateTime.now();
        modifiedDate = LocalDateTime.now();    //set the value of modifiedDate to LocalDateTIme.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
    }

    @Version
    Integer version;
}