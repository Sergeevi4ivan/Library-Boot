package ru.panas.springBootLibrary.LibraryBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.panas.springBootLibrary.LibraryBoot.models.Person;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);

    Optional<Person> findByUsername(String username);

    // Метод для проверки наличия админа в БД
    Optional<Person> findByRole(String role);


}
