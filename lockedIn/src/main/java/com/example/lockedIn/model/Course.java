package com.example.lockedIn.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "courses")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String code;

    @ManyToMany
    @JoinTable(name = "course_users", // Name of the join table
            joinColumns = @JoinColumn(name = "course_id"), // Foreign key column in the join table referencing Course
            inverseJoinColumns = @JoinColumn(name = "user_id") // Foreign key column in the join table referencing User
    )
    @JsonIgnoreProperties("courses")
    private List<User> users;

}
