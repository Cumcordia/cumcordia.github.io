package kz.goldenfish.goldenfish.controller;

import kz.goldenfish.goldenfish.model.User;
import kz.goldenfish.goldenfish.service.UserService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin-registration")
public class AdminAuthController {

    private final UserService userService;

    public AdminAuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAdminRegistrationForm() {
        return "admin/admin-registration";
    }

    @PostMapping
    public String processAdminRegistration(@ModelAttribute User user) {
        userService.saveAdmin(user);
        return "redirect:/login";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
