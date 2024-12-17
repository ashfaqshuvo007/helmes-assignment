package com.helmes.assignment.controllers.user;

import com.helmes.assignment.dto.SectorDTO;
import com.helmes.assignment.entity.models.MyUser;
import com.helmes.assignment.entity.models.Sector;
import com.helmes.assignment.services.SectorService;
import com.helmes.assignment.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/user/sectors")
public class UserSectorController {
    private static final String ALERT_TYPE = "alertType";
    private static final String ALERT_MESSAGE = "alertMessage";
    private static final String ALERT_SUCCESS = "success";
    private static final String ALERT_ERROR = "error";
    private final UserService userService;
    private final SectorService sectorService;

    public UserSectorController(UserService userService, SectorService sectorService) {
        this.userService = userService;
        this.sectorService = sectorService;
    }


    @GetMapping("/new")
    public String newUserSector(Model model, Authentication authentication){
        Map<SectorDTO, List<SectorDTO>> sectorsHierarchy = sectorService.getAllSectorsOrderedByParent();
        MyUser myUser = userService.loadUserByUsername(authentication.getName());
        model.addAttribute("sectorsHierarchy", sectorsHierarchy);

        model.addAttribute("user", myUser);
        model.addAttribute("userName", authentication.getName());

        return "user/sector/new";
    }

    @PostMapping("/add")
    public String addNewUserSector(@RequestParam List<Sector> sectors,
                                   @RequestParam String userTermsAndConditions,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes){
        try {
            userService.addSectors(authentication.getName(), sectors, userTermsAndConditions);
            redirectAttributes.addFlashAttribute(ALERT_TYPE,ALERT_SUCCESS);
            redirectAttributes.addFlashAttribute(ALERT_MESSAGE,
                "Assigned Sectors successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(ALERT_TYPE,ALERT_ERROR);
            redirectAttributes.addFlashAttribute(ALERT_MESSAGE, e.getMessage());
        }
        return "redirect:/user/sectors/new";
    }
}
