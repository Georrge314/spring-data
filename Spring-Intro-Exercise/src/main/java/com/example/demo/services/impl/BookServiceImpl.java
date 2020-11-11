package com.example.demo.services.impl;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.entities.*;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.repositories.BookRepository;
import com.example.demo.services.BookService;
import com.example.demo.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorServiceImpl authorService;
    private final CategoryServiceImpl categoryService;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, FileUtil fileUtil, AuthorRepository authorRepository, AuthorServiceImpl authorService, CategoryServiceImpl categoryService) {
        this.bookRepository = bookRepository;
        this.fileUtil = fileUtil;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBook() throws IOException {
        String[] fileContent = fileUtil.readFileContent(GlobalConstants.BOOKS_PATH);

        for (String row : fileContent) {
            String[] bookInfo = row.split("\\s+");

            Author author = getRandomAuthor();

            EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate releaseDate = LocalDate.parse(bookInfo[1], dateTimeFormatter);

            int copies = Integer.parseInt(bookInfo[2]);

            BigDecimal price = new BigDecimal(bookInfo[3]);

            Restriction restriction = Restriction.values()[Integer.parseInt(bookInfo[4])];

            StringBuilder titleBuilder = new StringBuilder();
            for (int i = 5; i < bookInfo.length; i++) {
                titleBuilder.append(bookInfo[i]).append(" ");
            }
            String title = titleBuilder.toString().trim();

            Set<Category> categories = getRandomCategories();

            Book book = new Book();
            book.setAuthor(author);
            book.setEditionType(editionType);
            book.setReleaseDate(releaseDate);
            book.setCopies(copies);
            book.setPrice(price);
            book.setRestriction(restriction);
            book.setTitle(title);
            book.setCategories(categories);

            bookRepository.save(book);
        }

        bookRepository.flush();
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        Random random = new Random();
        int bound = random.nextInt(3) + 1;
        for (int i = 1; i <= bound; i++) {
            categories.add(categoryService.getCategoryById(i));
        }
        return categories;
    }

    @Override
    public void getAllBooksAfter2000() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate localDate = LocalDate.parse("01/01/2000", dateTimeFormatter);
        List<Book> books = bookRepository.findAllByReleaseDateAfter(localDate);

        books.forEach(book -> System.out.println(book.getTitle()));
    }

    @Override
    public Set<Book> getBooksBefore1990() {
        Set<Book> books;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate localDate = LocalDate.parse("01/01/1990", dateTimeFormatter);
        books = bookRepository.findAllByReleaseDateBefore(localDate);

        return books;
    }


    private Author getRandomAuthor() {
        Random random = new Random();
        long randomId = random.nextInt(authorService.getAuthorsSize()) + 1;

        return authorService.getAuthorById(randomId);
    }


}
