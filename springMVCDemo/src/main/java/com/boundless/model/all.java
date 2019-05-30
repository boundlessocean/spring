package com.boundless.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class all {
     course course;
     student student;
     @DateTimeFormat(pattern = "yyyy-MM-dd")
     Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public com.boundless.model.course getCourse() {
        return course;
    }

    public void setCourse(com.boundless.model.course course) {
        this.course = course;
    }

    public com.boundless.model.student getStudent() {
        return student;
    }

    public void setStudent(com.boundless.model.student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "all{" +
                "course=" + course +
                ", student=" + student +
                ", date=" + date +
                '}';
    }
}
