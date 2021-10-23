package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(name = "total_time", columnDefinition = "tinyint")
    private Integer totalTime;
    @Column(columnDefinition = "tinyint")
    private Integer credit;
    @Column(name = "time_created")
    private LocalDateTime timeCreated;

    @ManyToOne
    private Instructor instructor;

    @ManyToMany(mappedBy = "courses")
    private Set<User> users;

    public Course(String name, Integer totalTime, Integer credit, LocalDateTime timeCreated) {
        this.name = name;
        this.totalTime = totalTime;
        this.credit = credit;
        this.timeCreated = timeCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }
}
