package com.softuni.springintroex.services;


import com.softuni.springintroex.entities.*;
import com.softuni.springintroex.repositories.BookRepo;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.softuni.springintroex.constants.GlobalConstants.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final FileUtil fileUtil;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo, FileUtil fileUtil, AuthorService authorService, CategoryService categoryService) {
        this.bookRepo = bookRepo;
        this.fileUtil = fileUtil;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }


    @Override
    public void seedBooks() throws IOException {
        String[] fileContent = fileUtil.readFileContent(BOOKS_FILE_PATH);

        for (String row : fileContent) {
            String[] bookInfo = row.split("\\s+");

            Author author = getRandomAuthor();

            EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate releaseDate = LocalDate.parse(bookInfo[1], dateTimeFormatter);

            int copies = Integer.parseInt(bookInfo[2]);

            BigDecimal price = new BigDecimal(bookInfo[3]);

            AgeRestriction restriction = AgeRestriction.values()[Integer.parseInt(bookInfo[4])];

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
            book.setAgeRestriction(restriction);
            book.setTitle(title);
            book.setCategories(categories);

            bookRepo.save(book);
        }

        bookRepo.flush();

    }

    @Override
    public void printTitlesByAge() throws IOException {
        String restriction = fileUtil.readLine().toUpperCase();
        List<Book> books = bookRepo.findBookByAgeRestriction(AgeRestriction.valueOf(restriction));

        books.forEach(book -> System.out.println(book.getTitle()));
    }

    @Override
    public void printGoldenBooks() {
        List<Book> books = bookRepo.findBookByEditionTypeAndCopiesIsLessThan(EditionType.GOLD, 5000);
        books.forEach(book -> System.out.println(book.getTitle()));
    }

    @Override
    public void printBooksByPrice() {
        List<Book> books = bookRepo.findBookByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40));
        books.forEach(book -> System.out.printf("%s - $%.2f%n", book.getTitle(), book.getPrice()));
    }

    @Override
    public void printNotReleasedBooks() throws IOException {
        Set<Book> books = bookRepo.findBooksByReleaseDateNotInYear(fileUtil.readLine());

        books.forEach(book -> System.out.println(book.getTitle()));
    }

    @Override
    public void printBooksReleasedBeforeDate() throws IOException {
        String date = fileUtil.readLine();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);

        Set<Book> books = bookRepo.findBooksByReleaseDateBefore(localDate);
        books.forEach(book -> System.out.printf("%s %s %.2f%n",
                book.getTitle(),
                book.getEditionType(),
                book.getPrice()));
    }

    @Override
    public void printBooksWhichContainsString() throws IOException {
        String pattern = "%" + fileUtil.readLine().toLowerCase() + "%";
        Set<Book> books = bookRepo.findBooksByTitleContaining(pattern);

        books.forEach(book -> System.out.println(book.getTitle()));
    }

    @Override
    public void printBookTittleSearch() throws IOException {
        String pattern = fileUtil.readLine() + "%";
        Set<Book> books = bookRepo.findBooksByAuthorLastNameEndWith(pattern);

        books.forEach(book -> System.out.printf("%s (%s %s)%n",
                book.getTitle(),
                book.getAuthor().getFirstName(),
                book.getAuthor().getLastName()));
    }

    @Override
    public void printCountOfBooksWithTitleLongerThan() throws IOException {
        int givenNumber = Integer.parseInt(fileUtil.readLine());
        int count = bookRepo.getCountOfBooksWhereTitleLengthIsLongerThan(givenNumber);
        System.out.printf("There are %d books longer title than %d symbols", count, givenNumber);
    }

    @Override
    public void printBookInfoByTitle() throws IOException {
        Book book = bookRepo.findBookByTitle(fileUtil.readLine());
        System.out.printf("%s %s %s %.2f%n",
                book.getTitle(),
                book.getEditionType(),
                book.getAgeRestriction(),
                book.getPrice());
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

    private Author getRandomAuthor() {
        Random random = new Random();
        long randomId = random.nextInt((int) authorService.getAuthorsSize()) + 1;

        return authorService.getAuthorById(randomId);
    }
}
