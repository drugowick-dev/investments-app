package dev.drugowick.investments.controllers.page_controllers;

import dev.drugowick.investments.services.UserService;
import dev.drugowick.investments.services.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class SettingsController {

    private final UserService userService;

    public SettingsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/settings"})
    public String settingsPage(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        Optional<UserDTO> optionalUser = userService.findOne(principal.getName());
        if (optionalUser.isPresent()) {
            model.addAttribute("profile", optionalUser.get());
        } else {
            throw new RuntimeException("Profile not found.");
        }
        return "settings";
    }
}
