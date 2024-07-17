package com.sudila.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;
    private String password;

    @JsonIgnore //meken wenne User gaddi me  issues list eka ganne nethuwa inna eka mokd issue list eke aye user gannawa eken aye issue gannawa eka iwara wenne ne
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    private List<Issue> assignedIssues = new ArrayList<>();

    private int projectSize;
}
