package ru.panas.springBootLibrary.LibraryBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.panas.springBootLibrary.LibraryBoot.models.Book;
import ru.panas.springBootLibrary.LibraryBoot.models.Person;


import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Person owner);

    List<Book> findBooksByTitleContaining(String request);

}
