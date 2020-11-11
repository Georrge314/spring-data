package com.example.demo.services;

import com.example.demo.entities.Author;

import java.io.IOException;

public interface AuthorService {
    void seedAuthor() throws IOException;
    int getAuthorsSize();
    Author getAuthorById(long id);

    void getAllAuthorsWithBookBefore1990();
}
