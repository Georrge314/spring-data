package com.softuni.springintroex.services;

import com.softuni.springintroex.entities.Author;

import java.io.IOException;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getAuthorById(long randomId);

    long getAuthorsSize();

    void printAuthorsEndWith() throws IOException;

    void printAuthorWithTotalCopies();
}
