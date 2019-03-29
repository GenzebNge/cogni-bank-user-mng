package com.cognibank.usermng.usermngspringmicroserviceapp.model;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "fieldName"})})
public class UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private String fieldName;
    @Column(nullable = false)
    private String fieldValue;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public UserDetails withId(Long id) {
        this.id = id;
        return this;
    }

    public UserDetails withUser(User user) {
        this.user = user;
        return this;
    }

    public UserDetails withFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public UserDetails withFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
        return this;
    }
}
