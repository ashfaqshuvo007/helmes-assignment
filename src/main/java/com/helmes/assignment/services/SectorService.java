package com.helmes.assignment.services;

import com.helmes.assignment.dto.SectorDTO;
import com.helmes.assignment.entity.models.Sector;
import com.helmes.assignment.entity.repositories.SectorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SectorService {
    private final SectorRepository sectorRepository;

    public SectorService(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    public List<SectorDTO> getAllSectors() {
        return sectorRepository.findAll().stream()
            .map(this::convertToDTO)
            .toList();
    }

    public Map<SectorDTO, List<SectorDTO>> getAllSectorsOrderedByParent() {
        List<SectorDTO> sectorDTOS = this.getAllSectors();
        Map<SectorDTO, List<SectorDTO>> sectorsHierarchy = new LinkedHashMap<>();

        for (SectorDTO sectorDTO : sectorDTOS) {
            if (sectorDTO.getParentName() == null) {
                sectorsHierarchy.put(sectorDTO,  new ArrayList<>());
            } else {
                for (Map.Entry<SectorDTO, List<SectorDTO>> entry : sectorsHierarchy.entrySet()) {
                    if (entry.getKey().getSectorName().equals(sectorDTO.getParentName())) {
                        entry.getValue().add(sectorDTO);
                        break;
                    }
                }
            }
        }
        return sectorsHierarchy;
    }

    public SectorDTO getSectorById(Long id) {
        return sectorRepository.findById(id).map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Sector not found"));
    }

    public void createSector(SectorDTO sectorDTO) {
        Sector parent = null;

        if (sectorDTO.getParentName() != null && !sectorDTO.getParentName().isEmpty()) {
            parent = sectorRepository.findAll().stream()
                .filter(sector -> sector.getSectorName().equals(sectorDTO.getParentName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Parent Sector not found"));
        }
        Sector sector = new Sector(sectorDTO.getId(),sectorDTO.getSectorName(), parent);
        convertToDTO(sectorRepository.save(sector));
    }

    public void updateSector(SectorDTO sectorDTO) {
        Sector sector = sectorRepository
            .findById(sectorDTO.getId()).orElseThrow(() -> new RuntimeException("Sector not found"));

        sector.setSectorName(sectorDTO.getSectorName());
        if (sectorDTO.getParentName() != null && !sectorDTO.getParentName().isEmpty()) {
            Sector parent = sectorRepository.findAll().stream()
                .filter(sec -> sec.getSectorName().equals(sectorDTO.getParentName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Parent Sector not found"));

            sector.setParent(parent);
        } else {
            sector.setParent(null);
        }
        sectorRepository.save(sector);
    }

    public void deleteSectorById(Long id) {
        if (!sectorRepository.existsById(id)) {
            throw new RuntimeException("Sector not found");
        }

        sectorRepository.deleteById(id);
    }

    private SectorDTO convertToDTO(Sector sector) {
        return new SectorDTO(
            sector.getId(),
            sector.getSectorName(),
            sector.getParent() != null ? sector.getParent().getSectorName() : null
        );
    }
}
