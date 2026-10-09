package com.siem.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int token;

    @Column(unique = true, nullable = false)
    private String userName;

    @JsonIgnore
    private String password;

    private String role;
    private String status;
}