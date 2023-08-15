package com.skosarev.library.controller;

import com.skosarev.library.model.Book;
import com.skosarev.library.model.Person;
import com.skosarev.library.service.BookService;
import com.skosarev.library.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(value = "sort", required = false, defaultValue = "default") String selectedSort) {

        Sort sort;
        switch (selectedSort) {
            case "title": {
                sort = Sort.by("title");
                break;
            }
            case "author": {
                sort = Sort.by("author");
                break;
            }
            case "year": {
                sort = Sort.by("year");
                break;
            }
            default:
                sort = Sort.unsorted();
        }

        model.addAttribute("sort", selectedSort);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", bookService.getTotalPages());
        model.addAttribute("books", bookService.getAllByPage(page, sort));

        return "/books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model) {
        Book book = bookService.get(id);
        model.addAttribute("book", book);

        if (book.getPerson() == null) {
            model.addAttribute("people", personService.getAll());
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

        bookService.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("book", bookService.get(id));
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/books/edit";
        }

        bookService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable int id) {
        bookService.release(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable int id, @RequestParam("owner") int personId) {
        Person person = personService.get(personId);
        bookService.setPerson(id, person);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
