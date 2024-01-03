package ru.panas.springBootLibrary.LibraryBoot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.panas.springBootLibrary.LibraryBoot.models.Person;
import ru.panas.springBootLibrary.LibraryBoot.services.PersonDetailService;

@Component
public class RegistrationValidator implements Validator {

    private final PersonDetailService personDetailService;

    @Autowired
    public RegistrationValidator(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        try {
            personDetailService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }
        errors.rejectValue("username", "", "Человек с таким логином уже существует");

    }
}
