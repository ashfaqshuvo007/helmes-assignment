package com.helmes.assignment.controllers;

import com.helmes.assignment.dto.SectorDTO;
import com.helmes.assignment.services.SectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SectorController {
    private static final String ALERT_TYPE = "alertType";
    private static final String ALERT_MESSAGE = "alertMessage";
    private static final String ALERT_SUCCESS = "success";
    private static final String ALERT_ERROR = "error";
    private static final String SECTORS = "sectors";

    private final SectorService sectorService;

    public SectorController(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @GetMapping("/sectors/new")
    public String newSector(Model model) {
        List<SectorDTO> sectors  = sectorService.getAllSectors();
        model.addAttribute("sectorDTO", new SectorDTO());
        model.addAttribute(SECTORS, sectors);

        return "admin/sector/new";
    }

    @PostMapping("/sectors")
    public String createSector(@ModelAttribute SectorDTO sectorDTO, Model model) {
        model.addAttribute(SECTORS, sectorService.getAllSectors());
        try {
            sectorService.createSector(sectorDTO);

            model.addAttribute(ALERT_TYPE,ALERT_SUCCESS);
            model.addAttribute(ALERT_MESSAGE,"Sector created successfully");
        } catch (Exception e) {
            model.addAttribute(ALERT_TYPE,ALERT_ERROR);
            model.addAttribute(ALERT_MESSAGE, e.getMessage());
        }
        return "admin/sector/new";
    }

    @GetMapping("/sectors/{id}/edit")
    public String editSector(@PathVariable Long id, Model model) {
        model.addAttribute("sectorDTO", sectorService.getSectorById(id));
        model.addAttribute(SECTORS, sectorService.getAllSectors());
        return "admin/sector/edit";
    }

    @PostMapping("/sector/update")
    public String updateSector(@ModelAttribute SectorDTO sectorDTO, Model model) {
        model.addAttribute(SECTORS, sectorService.getAllSectors());

        try {
            sectorService.updateSector(sectorDTO);
            model.addAttribute(ALERT_TYPE,ALERT_SUCCESS);
            model.addAttribute(ALERT_MESSAGE,"Sector updated successfully");
        } catch (Exception e) {
            model.addAttribute(ALERT_TYPE,ALERT_ERROR);
            model.addAttribute(ALERT_MESSAGE, e.getMessage());
        }
        return "admin/sector/edit";

    }

    @GetMapping("/sectors/{id}/delete")
    public String deleteSector(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            sectorService.deleteSectorById(id);
            redirectAttributes.addFlashAttribute(ALERT_TYPE,ALERT_SUCCESS);
            redirectAttributes.addFlashAttribute(ALERT_MESSAGE,"Sector deleted successfully");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(ALERT_TYPE,ALERT_ERROR);
            redirectAttributes.addFlashAttribute(ALERT_MESSAGE, e.getMessage());
        }
        return "redirect:/admin/home";
    }
}
