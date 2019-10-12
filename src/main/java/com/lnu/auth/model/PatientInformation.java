package com.lnu.auth.model;

import java.math.BigDecimal;
import java.util.List;

public class PatientInformation {
    private Long userId;
    private String username;
    private String email;
    private BigDecimal lat;
    private BigDecimal lng;
    private List<Therapy> therapy;

    public PatientInformation(Long userId, String username, String email, BigDecimal lat, BigDecimal lng, List<Therapy> therapy) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.lat = lat;
        this.lng = lng;
        this.therapy = therapy;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public List<Therapy> getTherapy() {
        return therapy;
    }
}
