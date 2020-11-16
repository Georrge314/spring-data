package com.softuni.springintroex.controllers;

import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class AppController implements CommandLineRunner {
    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public AppController(BookService bookService, AuthorService authorService,
                         CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        authorService.seedAuthors();
        categoryService.seedCategories();
        bookService.seedBooks();

//        bookService.printTitlesByAge();
//        bookService.printGoldenBooks();
//        bookService.printBooksByPrice();
//        bookService.printNotReleasedBooks();
//        bookService.printBooksReleasedBeforeDate();
//        authorService.printAuthorsEndWith();
//        bookService.printBooksWhichContainsString();
//        bookService.printBookTittleSearch();
//        bookService.printCountOfBooksWithTitleLongerThan();
//        authorService.printAuthorWithTotalCopies();
//        bookService.printBookInfoByTitle();

    }
}
