package com.helmes.assignment.controllers;

import com.helmes.assignment.dto.SectorDTO;
import com.helmes.assignment.services.SectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SectorController {
    private static final String ALERT_TYPE = "alertType";
    private static final String ALERT_MESSAGE = "alertMessage";

    private final SectorService sectorService;

    public SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @GetMapping("/sectors/new")
    public String newSector(Model model) {
        List<SectorDTO> sectors  = sectorService.getAllSectors();
        model.addAttribute("sectorDTO", new SectorDTO());
        model.addAttribute("sectors", sectors);

        return "admin/sector/new";
    }

    @PostMapping("/sectors")
    public String createSector(@ModelAttribute SectorDTO sectorDTO, Model model) {
        model.addAttribute("sectors", sectorService.getAllSectors());
        try {
            sectorService.createSector(sectorDTO);

            model.addAttribute(ALERT_TYPE,"success");
            model.addAttribute(ALERT_MESSAGE,"Sector created successfully");
        } catch (Exception e) {
            model.addAttribute(ALERT_TYPE,"error");
            model.addAttribute(ALERT_MESSAGE, e.getMessage());
        }
        return "admin/sector/new";
    }

    @GetMapping("/sectors/{id}/delete")
    public String deleteSector(@PathVariable Long id, Model model) {
        try {
            sectorService.deleteSectorById(id);
            model.addAttribute(ALERT_TYPE,"success");
            model.addAttribute(ALERT_MESSAGE,"Sector deleted successfully");

        } catch (Exception e) {
            model.addAttribute(ALERT_TYPE,"error");
            model.addAttribute(ALERT_MESSAGE, e.getMessage());
        }
        return "redirect:/admin/home";
    }
}
