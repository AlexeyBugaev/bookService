package com.alexVB.bookService.dao;

import com.alexVB.bookService.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookDAO {

    private static final RowMapper<Book> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Book.class);
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert bookInsert;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("books")
                .usingGeneratedKeyColumns("id");
    }

    public List<Book> getAllSorted(){
        List<Book> books;
        books = jdbcTemplate.query(
                "SELECT * FROM books ORDER BY title DESC", ROW_MAPPER);
        return books;
        }

    public List<Book> getAllGroupedByAuthor(){
        List<Book> books = getAllSorted();
        return books.stream().sorted(Comparator.comparing(Book::getAuthor))
                .collect(Collectors.toList());
    }

    public Book getBook(int id){
        return DataAccessUtils.singleResult(jdbcTemplate.query("SELECT * FROM books WHERE id=?", ROW_MAPPER, id));
    }

    @Transactional
    public Book addBook(Book book){
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("author", book.getAuthor())
                .addValue("description", book.getDescription());
        book.setId(bookInsert.executeAndReturnKey(map).intValue());
        return getBook(book.getId());
       //jdbcTemplate.update("INSERT INTO books(title, author, description) VALUES (?,?,?)", book.getTitle(), book.getAuthor(), book.getDescription());
    }
}
