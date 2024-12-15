package com.helmes.assignment.unit;

import com.helmes.assignment.dto.SectorDTO;
import com.helmes.assignment.entity.models.Sector;
import com.helmes.assignment.entity.repositories.SectorRepository;
import com.helmes.assignment.services.SectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SectorServiceTest {

    @Mock
    private SectorRepository sectorRepository;

    @InjectMocks
    private SectorService sectorService;

    @BeforeEach
    public void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void testGetAllSectorsReturnsSectorDTOList_Success() {
        Sector sector = new Sector(1L, "Technology", null);
        List<Sector> sectors = List.of(sector);
        when(sectorRepository.findAll()).thenReturn(sectors);

        List<SectorDTO> result = sectorService.getAllSectors();

        assertEquals(1, result.size());
        assertEquals("Technology", result.get(0).getSectorName());
    }

    @Test
    void testGetSectorByIDReturnsSectorDTO_Success() {
        Sector sector = new Sector(1L, "Technology", null);

        when(sectorRepository.findById(1L)).thenReturn(Optional.of(sector));
        SectorDTO result = sectorService.getSectorById(1L);
        assertNotNull(result);
        assertEquals("Technology", result.getSectorName());
        verify(sectorRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSectorByIdReturnsSectorDTOWithParent_Success() {
        Sector sector = new Sector(1L, "Technology", null);
        Sector sectorWithParent = new Sector(2L, "AI", sector);

        when(sectorRepository.findById(2L)).thenReturn(Optional.of(sectorWithParent));
        SectorDTO result = sectorService.getSectorById(2L);
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("AI", result.getSectorName());
        assertEquals("Technology", result.getParentName());
        verify(sectorRepository, times(1)).findById(2L);
    }

    @Test
    void testGetSectorById_NotFound() {
        when(sectorRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> sectorService.getSectorById(1L));
        assertNotNull(exception);
        assertEquals("Sector not found", exception.getMessage());
        verify(sectorRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateSector_Success() {
        Sector sector = new Sector(1L, "Technology", null);
        SectorDTO sectorDTO = new SectorDTO(1L, "Technology", null);
        when(sectorRepository.save(Mockito.any(Sector.class))).thenReturn(sector);
        sectorService.createSector(sectorDTO);
        verify(sectorRepository, times(1)).save(Mockito.any(Sector.class));

    }

    @Test
    void testCreateSectorWithParent_Failure() {
        Sector sector = new Sector(1L, "Technology", null);
        Sector sectorWithParent = new Sector(2L, "AI", sector);
        SectorDTO sectorWithParentDTO = new SectorDTO(1L, "Technology", sector.getSectorName());

        when(sectorRepository.save(Mockito.any(Sector.class))).thenReturn(sectorWithParent);

        Exception exception = assertThrows(RuntimeException.class, () -> sectorService.createSector(sectorWithParentDTO));
        assertNotNull(exception);
        assertEquals("Parent Sector not found", exception.getMessage());

    }

    @Test
    void testUpdateSector_Success() {
        Sector sector = new Sector(1L, "Technology", null);
        Sector updateSector = new Sector(1L, "Technology Updated", null);
        SectorDTO updatedSectorDTO = new SectorDTO(1L, "Technology Updated", null);

        when(sectorRepository.findById(1L)).thenReturn(Optional.of(sector));
        when(sectorRepository.save(Mockito.any(Sector.class))).thenReturn(updateSector);

        sectorService.updateSector(updatedSectorDTO);
        verify(sectorRepository, times(1)).findById(1L);
        verify(sectorRepository, times(1)).save(sector);

        assertEquals("Technology Updated", updateSector.getSectorName());
    }

    @Test
    void testUpdateSectorWithParent_Failure() {
        Sector existingSector = new Sector(1L, "Technology", null);
        SectorDTO updatedSectorDTO = new SectorDTO(1L, "Technology Updated", "Not found parent");

        when(sectorRepository.findById(1L)).thenReturn(Optional.of(existingSector));
        when(sectorRepository.findAll()).thenReturn(List.of());

        Exception exception = assertThrows(RuntimeException.class, () -> sectorService.updateSector(updatedSectorDTO));
        assertNotNull(exception);
        assertEquals("Parent Sector not found", exception.getMessage());
    }

    @Test
    void testUpdateSector_Failure() {
        SectorDTO sectorDTO = new SectorDTO();
        sectorDTO.setId(1L);

        when(sectorRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> sectorService.updateSector(sectorDTO));
        assertNotNull(exception);
        assertEquals("Sector not found", exception.getMessage());
        verify(sectorRepository, times(1)).findById(1L);

    }

    @Test
    void deleteSectorById_ShouldDeleteSector() {
        when(sectorRepository.existsById(1L)).thenReturn(true);
        sectorService.deleteSectorById(1L);
        verify(sectorRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteSectorById_ShouldThrowException_WhenSectorNotFound() {
        when(sectorRepository.existsById(1L)).thenReturn(false);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sectorService.deleteSectorById(1L);
        });
        assertEquals("Sector not found", exception.getMessage());
    }

}
