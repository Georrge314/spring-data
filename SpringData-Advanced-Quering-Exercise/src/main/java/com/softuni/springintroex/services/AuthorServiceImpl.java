package com.softuni.springintroex.services;

import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.entities.Author;
import com.softuni.springintroex.entities.Book;
import com.softuni.springintroex.repositories.AuthorRepo;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.softuni.springintroex.constants.GlobalConstants.*;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepo authorRepo;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepo authorRepo, FileUtil fileUtil) {
        this.authorRepo = authorRepo;
        this.fileUtil = fileUtil;
    }

    public void seedAuthors() throws IOException {
        String[] fileContent = fileUtil.readFileContent(AUTHORS_FILE_PATH);

        Arrays.stream(fileContent).forEach(row -> {
            String[] params = row.split("\\s+");
            List<Author> authorList = authorRepo.findAll();
            boolean isUnique = true;
            for (Author author : authorList) {
                if (author.getLastName().equals(params[1])) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                Author author = new Author(params[0], params[1]);
                authorRepo.saveAndFlush(author);
            }
        });
    }

    @Override
    public Author getAuthorById(long randomId) {
      return authorRepo.findAuthorById(randomId);
    }

    public long getAuthorsSize() {
        return authorRepo.count();
    }

    @Override
    public void printAuthorsEndWith() throws IOException {
        Set<Author> authors = authorRepo.findAuthorsByFirstNameEndingWith(fileUtil.readLine());
        authors.forEach(author -> System.out.println(author.getFirstName() + " " + author.getLastName()));
    }

    @Override
    public void printAuthorWithTotalCopies() {
        Set<Author> authors = authorRepo.findAllBy();
        Map<String, Integer> authorsCopies = new HashMap<>();
        authors.forEach(author -> {
            int copies = author.getBooks().stream().mapToInt(Book::getCopies).sum();
            authorsCopies.put(author.getFirstName() + " " + author.getLastName(), copies);
        });

        authorsCopies.entrySet().stream()
                .sorted((e1,e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(entry -> System.out.printf("%s - %d%n",entry.getKey(),entry.getValue()));
    }


}
