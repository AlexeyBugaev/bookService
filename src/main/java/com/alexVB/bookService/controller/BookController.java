package com.alexVB.bookService.controller;

import com.alexVB.bookService.dao.BookDAO;
import com.alexVB.bookService.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class BookController {

    @Autowired
    private BookDAO bookDAO;

    @GetMapping(value = "rest/books/getAll/sorted", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAllSorted(){
        return bookDAO.getAllSorted();
    }

    @GetMapping(value = "rest/books/getAll/groupedByAuthor", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAllGroupedByAuthor(){
        return bookDAO.getAllGroupedByAuthor();
    }

    @PostMapping(value = "rest/books/addNew", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book addBook(@RequestBody Book book){
        return bookDAO.addBook(book);
    }
}
