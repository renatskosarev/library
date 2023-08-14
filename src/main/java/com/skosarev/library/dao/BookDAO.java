package com.skosarev.library.dao;

import com.skosarev.library.model.Book;
import com.skosarev.library.util.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM Books", new BookMapper());
    }

    public Book get(int id) {
        return jdbcTemplate.query("SELECT * FROM Books WHERE id = ?", new BookMapper(), id)
                .stream().findAny().orElse(null);
    }

    public List<Book> getByOwnerId(int id) {
        return jdbcTemplate.query("SELECT * FROM Books WHERE owner_id = ?", new BookMapper(), id);
    }

    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO Books(title, author, year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE Books SET owner_id = ?, title = ?, author = ?, year = ? WHERE id = ?",
                book.getOwnerId() == 0 ? null : book.getOwnerId(), book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE Books SET owner_id = null WHERE id = ?", id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Books WHERE id = ?", id);
    }
}
