package dev.drugowick.investments.controllers.page_controllers;

import dev.drugowick.investments.services.UserService;
import dev.drugowick.investments.services.dto.UserDTO;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
        Optional<UserDTO> optionalUser =
                userService.findByProviderAndProviderId(
                        ((OAuth2AuthenticationToken) principal).getAuthorizedClientRegistrationId(),
                        principal.getName());

        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
        } else {
            throw new RuntimeException("Profile not found.");
        }
        return "settings";
    }

    @PostMapping({"/settings"})
    public String saveSettings(
            Principal principal,
            @Valid UserDTO userDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", "Error saving settings.");
        redirectAttributes.addFlashAttribute("type", "danger");
        if (result.hasErrors())
            return "redirect:/settings";

        userService.save(userDTO);
        redirectAttributes.addFlashAttribute("message", "Settings successfully saved.");
        redirectAttributes.addFlashAttribute("type", "success");
        return "redirect:/settings";
    }
}
