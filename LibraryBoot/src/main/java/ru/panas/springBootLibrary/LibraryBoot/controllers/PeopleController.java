package ru.panas.springBootLibrary.LibraryBoot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.panas.springBootLibrary.LibraryBoot.models.Book;
import ru.panas.springBootLibrary.LibraryBoot.models.Person;
import ru.panas.springBootLibrary.LibraryBoot.services.BookService;
import ru.panas.springBootLibrary.LibraryBoot.services.PeopleService;
import ru.panas.springBootLibrary.LibraryBoot.services.RegistrationService;
import ru.panas.springBootLibrary.LibraryBoot.util.PersonValidator;


import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final BookService bookService;
    private final PersonValidator personValidator;

    private final RegistrationService registrationService;

    @Autowired
    public PeopleController(PeopleService peopleService, BookService bookService, PersonValidator personValidator, RegistrationService registrationService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
        this.personValidator = personValidator;
        this.registrationService = registrationService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Person person = peopleService.findOne(id);
        List<Book> books = bookService.findByOwner(person);

        model.addAttribute("person", person);
        model.addAttribute("books", books);

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        registrationService.register(person);
        return "redirect:auth/login";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
