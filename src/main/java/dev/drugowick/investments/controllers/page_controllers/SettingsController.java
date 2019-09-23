package dev.drugowick.investments.controllers.page_controllers;

import dev.drugowick.investments.services.ProfileService;
import dev.drugowick.investments.services.dto.ProfileDTO;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class SettingsController {

    private final ProfileService profileService;
    private final UserDetailsManager userDetailsManager;

    public SettingsController(ProfileService profileService, UserDetailsManager userDetailsManager) {
        this.profileService = profileService;
        this.userDetailsManager = userDetailsManager;
    }

    @GetMapping({"/settings"})
    public String settingsPage(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        Optional<ProfileDTO> optionalProfile = profileService.findOne(1L);
        if (optionalProfile.isPresent()) {
            model.addAttribute("profile", optionalProfile.get());
        } else {
            throw new RuntimeException("Profile not found.");
        }
        return "settings";
    }
}
