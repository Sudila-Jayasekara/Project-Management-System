package com.sudila.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data // Lombok annotation to generate getters, setters, toString, etc.
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;
    private String password;

    @JsonIgnore // Ignores JSON serialization of assignedIssues field to prevent circular dependencies
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL) // One user can have many assigned issues
    private List<Issue> assignedIssues = new ArrayList<>(); // List of issues assigned to this user

    private int projectSize; // Size of the project related to the user
}
