package com.example.msusers.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    private String name;
    private String lastName;
    private String email;
    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private BillDTO subscription;

    public User(String id, String name, String lastName, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }
    public User(String id, String name, String lastName, String email,BillDTO subscription) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.subscription = subscription;
    }
    public BillDTO getSubscriptionDTO() {
        return subscription;
    }

    public void setSubscription(BillDTO subscription) {
        this.subscription = subscription;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}