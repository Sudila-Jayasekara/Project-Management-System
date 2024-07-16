package com.sudila.modal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // Lombok annotation to generate getters, setters, toString, etc.
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Unique identifier for the issue

    @ManyToOne // Many issues can be assigned to one user
    private User assignee; // User assigned to this issue
}
