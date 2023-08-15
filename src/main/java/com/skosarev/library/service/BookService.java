package com.skosarev.library.service;

import com.skosarev.library.model.Book;
import com.skosarev.library.model.Person;
import com.skosarev.library.repository.BookRepository;
import com.skosarev.library.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book get(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void create(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void release(int id) {
        bookRepository.release(id);
    }

    @Transactional
    public void setPerson(int id, Person newPerson) {
        Person person = personRepository.findById(newPerson.getId()).orElse(null);
        bookRepository.assign(id, person);
    }
}
