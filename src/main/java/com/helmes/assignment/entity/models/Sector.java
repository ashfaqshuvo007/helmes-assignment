package com.helmes.assignment.entity.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sectors")
public class Sector {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "sector_name", unique = true, nullable = false)
    private String sectorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Sector parent;

    public Sector(Long id, String sectorName, Sector parent) {
        this.id = id;
        this.sectorName = sectorName;
        this.parent = parent;
    }

    public Sector() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public Sector getParent() {
        return parent;
    }

    public void setParent(Sector parent) {
        this.parent = parent;
    }


}
