package dev.drugowick.investments.controllers.page_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnotherPageController {

    @GetMapping({"/another-page", "another-page.html"})
    public String indexPage() {
        return "anotherpage";
    }
}
