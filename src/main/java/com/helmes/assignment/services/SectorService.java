package com.helmes.assignment.services;

import com.helmes.assignment.dto.SectorDTO;
import com.helmes.assignment.entity.repositories.SectorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectorService {
    private final SectorRepository sectorRepository;

    public SectorService(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    public List<SectorDTO> getAllSectors() {
        return sectorRepository.findAll().stream()
            .map(sector -> new SectorDTO(
                sector.getId(),
                sector.getSectorName(),
                sector.getParent() != null ? sector.getParent().getSectorName() : null
            ))
            .collect(Collectors.toList());
    }
}
