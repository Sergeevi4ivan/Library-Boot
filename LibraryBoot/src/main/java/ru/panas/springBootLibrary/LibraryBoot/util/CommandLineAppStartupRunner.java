package ru.panas.springBootLibrary.LibraryBoot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.panas.springBootLibrary.LibraryBoot.models.Book;
import ru.panas.springBootLibrary.LibraryBoot.models.Person;
import ru.panas.springBootLibrary.LibraryBoot.repositories.BooksRepository;
import ru.panas.springBootLibrary.LibraryBoot.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

// Класс для добавления админа и список кник для примера при запуске приложения

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final PeopleRepository peopleRepository;

    private final BooksRepository booksRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CommandLineAppStartupRunner(PeopleRepository peopleRepository, BooksRepository booksRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {

        Date createdDate = new Date();

        List <Optional<Person>> getAdmin = peopleRepository.findByRole("ROLE_ADMIN");

        if (getAdmin.isEmpty()) {

            // добавляет администратора при запуске приложения
            String encodedPassword = passwordEncoder.encode("admin");

            Person personAdmin = new Person();
            personAdmin.setName("Admin");
            personAdmin.setAge(24);
            personAdmin.setDateOfBirth(new Date());
            personAdmin.setCreatedAt(createdDate);
            personAdmin.setUsername("admin");
            personAdmin.setPassword(encodedPassword);
            personAdmin.setRole("ROLE_ADMIN");

            peopleRepository.save(personAdmin);

            // добавляет список книг для примера
            Book book1 = new Book("Психопатология обыденной жизни", "Зигмунд Фрейд", 1904);
            book1.setAssignAt(createdDate);
            booksRepository.save(book1);

            Book book2 = new Book("Война и мир", "Лев Николаевич Толстой", 1873);
            book2.setAssignAt(createdDate);
            booksRepository.save(book2);

            Book book3 = new Book("Герой нашего времени", "Михаил Юрьевич Лермонтов", 1841);
            book3.setAssignAt(createdDate);
            booksRepository.save(book3);

            Book book4 = new Book("му-му", "Иван Сергеевич Тургенев", 1852);
            book4.setAssignAt(createdDate);
            booksRepository.save(book4);

            Book book5 = new Book("Дубровский", "Александр Сергеевич Пушкин", 1841);
            book5.setAssignAt(createdDate);
            booksRepository.save(book5);
        }
    }
}
