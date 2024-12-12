package com.helmes.assignment.services;

import com.helmes.assignment.dto.SectorDTO;
import com.helmes.assignment.entity.models.Sector;
import com.helmes.assignment.entity.repositories.SectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorService {
    private static final Logger log = LoggerFactory.getLogger(SectorService.class);
    private final SectorRepository sectorRepository;

    public SectorService(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    public List<SectorDTO> getAllSectors() {
        return sectorRepository.findAll().stream()
            .map(this::convertToDTO)
            .toList();
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
                .orElseThrow(() -> new RuntimeException("Sector not found"));
        }
        Sector sector = new Sector(sectorDTO.getId(),sectorDTO.getSectorName(), parent);
        convertToDTO(sectorRepository.save(sector));
    }

    public void deleteSectorById(Long id) {
        if (!sectorRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
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
