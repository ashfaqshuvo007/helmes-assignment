package com.helmes.assignment.entity.models;

import com.helmes.assignment.enums.Role;
import com.helmes.assignment.enums.TermsAndConditionsAgreement;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;


@Entity
@Table(name="users")
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @ManyToMany
    @JoinTable(
        name = "user_sectors",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "sector_id")
    )
    private List<Sector> sectors;

    @Column(name = "user_terms_and_conditions")
    @Enumerated(EnumType.STRING)
    private TermsAndConditionsAgreement userTermsAndConditions;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Sector> getSectors() {
        return sectors;
    }

    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
    }

    public TermsAndConditionsAgreement getUserTermsAndConditions() {
        return userTermsAndConditions;
    }

    public void setUserTermsAndConditions(TermsAndConditionsAgreement userTermsAndConditions) {
        this.userTermsAndConditions = userTermsAndConditions;
    }
}
