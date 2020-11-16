package com.softuni.springintroex.services;

import java.io.IOException;

public interface BookService {
    void seedBooks() throws IOException;

    void printTitlesByAge() throws IOException;

    void printGoldenBooks();

    void printBooksByPrice();

    void printNotReleasedBooks() throws IOException;

    void printBooksReleasedBeforeDate() throws IOException;

    void printBooksWhichContainsString() throws IOException;

    void printBookTittleSearch() throws IOException;

    void printCountOfBooksWithTitleLongerThan() throws IOException;

    void printBookInfoByTitle() throws IOException;
}
