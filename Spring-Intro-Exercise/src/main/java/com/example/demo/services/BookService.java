package com.example.demo.services;

import com.example.demo.entities.Book;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BookService {
    void seedBook() throws IOException;

    void getAllBooksAfter2000();

    Set<Book> getBooksBefore1990();

}
