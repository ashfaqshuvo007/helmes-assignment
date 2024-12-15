package com.helmes.assignment.integration;


import com.helmes.assignment.controllers.SectorController;
import com.helmes.assignment.dto.SectorDTO;
import com.helmes.assignment.services.SectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SectorControllerTests {

    private MockMvc mockMvc;

    private SectorService sectorService;

    @BeforeEach
    void setUp() {
        sectorService = Mockito.mock(SectorService.class);
        SectorController sectorController = new SectorController(sectorService);
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sectorController).build();
    }

    @Test
    void testNewSector() throws Exception {
        when(sectorService.getAllSectors()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/sectors/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/sector/new"))
            .andExpect(model().attributeExists("sectorDTO"))
            .andExpect(model().attribute("sectors", Collections.emptyList()));

        verify(sectorService, times(1)).getAllSectors();
    }

    @Test
    void testCreateSectorSuccess() throws Exception {
        SectorDTO parentSector1 = new SectorDTO(1L, "Parent Sector 1", null);
        SectorDTO parentSector2 = new SectorDTO(2L, "Parent Sector 2", null);
        List<SectorDTO> parentSectors = Arrays.asList(parentSector1, parentSector2);

        doNothing().when(sectorService).createSector(any(SectorDTO.class));
        when(sectorService.getAllSectors()).thenReturn(parentSectors);

        mockMvc.perform(post("/sectors")
                .param("name", "New Sector")
                .param("parentSectorId", "1"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/sector/new"))
            .andExpect(model().attribute("alertType", "success"))
            .andExpect(model().attribute("alertMessage", "Sector created successfully"))
            .andExpect(model().attribute("sectors", parentSectors));

        verify(sectorService, times(1)).createSector(any(SectorDTO.class));
        verify(sectorService, times(1)).getAllSectors();
    }

    @Test
    void testCreateSectorFailure() throws Exception {
        doThrow(new RuntimeException("Error creating sector"))
            .when(sectorService).createSector(any(SectorDTO.class));
        when(sectorService.getAllSectors()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/sectors")
                .param("name", "Invalid Sector"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/sector/new"))
            .andExpect(model().attribute("alertType", "error"))
            .andExpect(model().attribute("alertMessage", "Error creating sector"))
            .andExpect(model().attribute("sectors", Collections.emptyList()));

        verify(sectorService, times(1)).createSector(any(SectorDTO.class));
        verify(sectorService, times(1)).getAllSectors();
    }

    @Test
    void testEditSector() throws Exception {
        SectorDTO mockSector = new SectorDTO();
        when(sectorService.getSectorById(1L)).thenReturn(mockSector);
        when(sectorService.getAllSectors()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/sectors/1/edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/sector/edit"))
            .andExpect(model().attribute("sectorDTO", mockSector))
            .andExpect(model().attribute("sectors", Collections.emptyList()));

        verify(sectorService, times(1)).getSectorById(1L);
        verify(sectorService, times(1)).getAllSectors();
    }

    @Test
    void testUpdateSectorSuccess() throws Exception {
        doNothing().when(sectorService).updateSector(any(SectorDTO.class));
        when(sectorService.getAllSectors()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/sector/update")
                .param("id", "1")
                .param("name", "Updated Sector"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/sector/edit"))
            .andExpect(model().attribute("alertType", "success"))
            .andExpect(model().attribute("alertMessage", "Sector updated successfully"))
            .andExpect(model().attribute("sectors", Collections.emptyList()));

        verify(sectorService, times(1)).updateSector(any(SectorDTO.class));
        verify(sectorService, times(1)).getAllSectors();
    }

    @Test
    void testUpdateSectorFailure() throws Exception {
        doThrow(new RuntimeException("Error updating sector"))
            .when(sectorService).updateSector(any(SectorDTO.class));
        when(sectorService.getAllSectors()).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/sector/update")
                .param("id", "1")
                .param("name", "Invalid Sector"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/sector/edit"))
            .andExpect(model().attribute("alertType", "error"))
            .andExpect(model().attribute("alertMessage", "Error updating sector"))
            .andExpect(model().attribute("sectors", Collections.emptyList()));

        verify(sectorService, times(1)).updateSector(any(SectorDTO.class));
        verify(sectorService, times(1)).getAllSectors();
    }

    @Test
    void testDeleteSectorSuccess() throws Exception {
        doNothing().when(sectorService).deleteSectorById(1L);

        mockMvc.perform(get("/sectors/1/delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/home"))
            .andExpect(flash().attribute("alertType", "success"))
            .andExpect(flash().attribute("alertMessage", "Sector deleted successfully"));

        verify(sectorService, times(1)).deleteSectorById(1L);
    }

    @Test
    void testDeleteSectorFailure() throws Exception {
        doThrow(new RuntimeException("Error deleting sector"))
            .when(sectorService).deleteSectorById(1L);

        mockMvc.perform(get("/sectors/1/delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/home"))
            .andExpect(flash().attribute("alertType", "error"))
            .andExpect(flash().attribute("alertMessage", "Error deleting sector"));

        verify(sectorService, times(1)).deleteSectorById(1L);
    }
}
