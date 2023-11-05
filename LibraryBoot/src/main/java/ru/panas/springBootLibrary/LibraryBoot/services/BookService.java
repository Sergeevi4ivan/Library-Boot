package ru.panas.springBootLibrary.LibraryBoot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.panas.springBootLibrary.LibraryBoot.models.Book;
import ru.panas.springBootLibrary.LibraryBoot.models.Person;
import ru.panas.springBootLibrary.LibraryBoot.repositories.BooksRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public List<Book> findAll(Pageable pageable) {
        return booksRepository.findAll(pageable).getContent();
    }

    public List<Book> findAllSort(String sortColumn) {
        return booksRepository.findAll(Sort.by(sortColumn));
    }

    public List<Book> findBooksByTitleContaining(String title) {
        return booksRepository.findBooksByTitleContaining(title);
    }


    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updateBook) {
        updateBook.setBookId(id);
        booksRepository.save(updateBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public Optional<Person> getBookOwner(int id) {
        Optional<Book> book = booksRepository.findById(id);

        if (book.get().getAssignAt() != null) {
            long timeOfAssign = new Date().getTime() - book.get().getAssignAt().getTime();

            if (timeOfAssign > TimeUnit.MILLISECONDS.convert(10, TimeUnit.DAYS)) {
                book.get().setLate(true);
            }
        }
        return book.map(value -> Optional.ofNullable(value.getOwner())).orElse(null);
    }

    @Transactional
    public void release(int id) {
        Optional<Book> book = booksRepository.findById(id);

        book.ifPresent(value -> value.setAssignAt(null));
        book.ifPresent(value -> value.setOwner(null));
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Optional<Book> book = booksRepository.findById(id);

        book.ifPresent(value -> value.setAssignAt(new Date()));
        book.ifPresent(value -> value.setOwner(selectedPerson));
    }

    public List<Book> findByOwner(Person owner) {
        return booksRepository.findByOwner(owner);
    }

}
