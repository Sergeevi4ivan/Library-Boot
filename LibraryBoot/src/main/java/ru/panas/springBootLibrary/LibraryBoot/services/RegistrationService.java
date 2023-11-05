package ru.panas.springBootLibrary.LibraryBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.panas.springBootLibrary.LibraryBoot.models.Person;
import ru.panas.springBootLibrary.LibraryBoot.repositories.PeopleRepository;

import java.util.Date;
import java.util.Optional;


@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {

        // Если нет ни одного админа, то создаётся админ по умолчанию
        Optional<Person> getAdmin = peopleRepository.findByRole("ROLE_ADMIN");

        if (getAdmin.isEmpty()) {
            createAdmin();
        }

        // Регистрация нового пользователя
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);
        person.setCreatedAt(new Date());
        person.setRole("ROLE_USER");

        peopleRepository.save(person);
    }

    // Метод создаёт админа по умолчанию
    private void createAdmin() {
        Person personAdmin = new Person();
        personAdmin.setName("Admin");
        personAdmin.setAge(24);
        personAdmin.setDateOfBirth(new Date());
        personAdmin.setCreatedAt(new Date());
        personAdmin.setUsername("admin");
        personAdmin.setPassword("admin");
        personAdmin.setRole("ROLE_ADMIN");

        peopleRepository.save(personAdmin);

    }
}
