package com.cognibank.usermng.usermngspringmicroserviceapp.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;
    @Column(nullable = false)
    private UserType type;
    @Column(nullable = false, unique = true)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean active;
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<UserDetails> details;

    public String getId() {
        return id;
    }

    public UserType getType() {
        return type;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getActive() {
        return active;
    }

    public List<UserDetails> getDetails() {
        return details;
    }

    public User withId(String id) {
        this.id = id;
        return this;
    }

    public User withType(UserType type) {
        this.type = type;
        return this;
    }

    public User withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public User withPassword(String password) {
        this.password = password;
        return this;
    }

    public User withUserDetails(List<UserDetails> details) {
        this.details = details;
        return this;
    }

    public User withActive(Boolean active) {
        this.active = active;
        return this;
    }
}
