package com.helmes.assignment.controllers;

import com.helmes.assignment.dto.SectorDTO;
import com.helmes.assignment.entity.models.MyUser;
import com.helmes.assignment.entity.repositories.MyUserRepository;
import com.helmes.assignment.enums.Role;
import com.helmes.assignment.services.SectorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ContentController {
    private final MyUserRepository myUserRepository;
    private final SectorService sectorService;

    public ContentController(MyUserRepository myUserRepository, SectorService sectorService) {
        this.myUserRepository = myUserRepository;
        this.sectorService = sectorService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin/home")
    public String adminHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<MyUser> users = myUserRepository.findAllByRole(Role.USER);
        List<SectorDTO> sectors = sectorService.getAllSectors();

        model.addAttribute("users", users);
        model.addAttribute("sectors", sectors);
        model.addAttribute("userName", authentication.getName());

        return "admin/home";
    }

    @GetMapping("/user/home")
    public String userHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<SectorDTO, List<SectorDTO>> sectorsHierarchy = sectorService.getAllSectorsOrderedByParent();
        model.addAttribute("userName", authentication.getName());
        model.addAttribute("sectorsHierarchy", sectorsHierarchy);
        return "user/home";
    }

}
