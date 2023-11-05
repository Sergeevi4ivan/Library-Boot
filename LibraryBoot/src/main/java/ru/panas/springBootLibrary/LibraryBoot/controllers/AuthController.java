package ru.panas.springBootLibrary.LibraryBoot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.panas.springBootLibrary.LibraryBoot.models.Person;
import ru.panas.springBootLibrary.LibraryBoot.services.RegistrationService;
import ru.panas.springBootLibrary.LibraryBoot.util.RegistrationValidator;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationValidator registrationValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(RegistrationValidator registrationValidator, RegistrationService registrationService) {
        this.registrationValidator = registrationValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person")Person person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person,
                                      BindingResult bindingResult) {
        registrationValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        registrationService.register(person);

        return "redirect:auth/login";
    }
}
