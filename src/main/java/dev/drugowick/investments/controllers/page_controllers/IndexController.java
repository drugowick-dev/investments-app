package dev.drugowick.investments.controllers.page_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

    @GetMapping({"", "/", "index", "index.html"})
    public String indexPage(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "index";
    }
}
