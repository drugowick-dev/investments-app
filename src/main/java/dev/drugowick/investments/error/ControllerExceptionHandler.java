package dev.drugowick.investments.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String exceptionHandling(Model model, Exception exception) {
        model.addAttribute("type", exception.getClass());
        model.addAttribute("message", exception.getLocalizedMessage());
        exception.printStackTrace();
        return "error";
    }
}
