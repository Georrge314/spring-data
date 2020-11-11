package com.example.demo.services.impl;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.entities.Author;
import com.example.demo.entities.Book;
import com.example.demo.repositories.AuthorRepository;
import com.example.demo.services.AuthorService;
import com.example.demo.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;
    private final BookServiceImpl bookService;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil, BookServiceImpl bookService) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
        this.bookService = bookService;
    }

    @Override
    public void seedAuthor() throws IOException {
        String[] fileContent = fileUtil.readFileContent(GlobalConstants.AUTHORS_PATH);

        Arrays.stream(fileContent).forEach(row -> {
            String[] params = row.split("\\s+");
            List<Author> authorList = authorRepository.findAll();
            boolean isUnique = true;
            for (Author author : authorList) {
                if (author.getLastName().equals(params[1])) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                Author author = new Author(params[0], params[1]);
                authorRepository.saveAndFlush(author);
            }
        });

    }

    @Override
    public int getAuthorsSize() {
       return (int) authorRepository.count();
    }

    @Override
    public Author getAuthorById(long id) {
        return authorRepository.getOne(id);
    }

    @Override
    public void getAllAuthorsWithBookBefore1990() {
        List<Author> authors = authorRepository.findAuthorsByBooksBefore((Set<Book>) bookService.getBooksBefore1990());
        authors.forEach(author -> System.out.printf("%s %s%n",author.getFirstName(),author.getLastName()));
    }
}
