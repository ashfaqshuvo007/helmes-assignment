package com.helmes.assignment.dto;

public class SectorDTO {
    private Long id;
    private String sectorName;
    private String parentName;

    public SectorDTO(Long id, String sectorName, String parentName) {
        this.id = id;
        this.sectorName = sectorName;
        this.parentName = parentName;
    }

    public SectorDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String name) {
        this.sectorName = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
