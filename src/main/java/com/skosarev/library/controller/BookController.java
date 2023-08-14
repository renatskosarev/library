package com.skosarev.library.controller;

import com.skosarev.library.dao.BookDAO;
import com.skosarev.library.dao.PersonDAO;
import com.skosarev.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model) {
        List<Book> books = bookDAO.getAll();
        model.addAttribute("books", books);

        return "/books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        Book book = bookDAO.get(id);
        model.addAttribute("book", book);

        if (book.getOwnerId() == 0) {
            model.addAttribute("people", personDAO.getAll());
        } else {
            model.addAttribute("person", personDAO.get(bookDAO.get(id).getOwnerId()));
        }

        return "/books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "/books/new";
    }

    @PostMapping
    public String create(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/books/new";
        }

        bookDAO.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("book", bookDAO.get(id));
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/books/edit";
        }

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable int id) {
        bookDAO.release(id);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
