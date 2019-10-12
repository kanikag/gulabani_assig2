package com.lnu.auth.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name="userID")
    private Long userId;

    private String username;

    private String email;

    @ManyToOne
    @JoinColumn(name = "Role_IDrole")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "Organization")
    private Organization organization;

    @Column(name = "Lat")
    private BigDecimal lat;

    @Column(name = "Long")
    private BigDecimal lng;

    @OneToMany(mappedBy = "doctor")
    private List<Therapy> therapies;

    @OneToMany(mappedBy = "patient")
    private List<Therapy> patientTherapies;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public List<Therapy> getTherapies() {
        return therapies;
    }

    public void setTherapies(List<Therapy> therapies) {
        this.therapies = therapies;
    }

    public List<Therapy> getPatientTherapies() {
        return patientTherapies;
    }

    public void setPatientTherapies(List<Therapy> patientTherapies) {
        this.patientTherapies = patientTherapies;
    }
}
