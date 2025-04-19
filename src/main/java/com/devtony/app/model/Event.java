package com.devtony.app.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    private Instant date;
    private String location;
    private String description;
    private String image;

    @ManyToOne()
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "event")
    private Set<EventInvitation> invitations = new HashSet<>();

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Instant getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<EventInvitation> getInvitations() {
        return invitations;
    }
}
