package softuni.library.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.library.models.dto.jsons.BookImportDto;
import softuni.library.models.entities.Author;
import softuni.library.models.entities.Book;
import softuni.library.repositories.AuthorRepository;
import softuni.library.repositories.BookRepository;
import softuni.library.services.BookService;
import softuni.library.util.validator.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    public static final String BOOKS_JSON_PATH = "src/main/resources/files/json/books.json";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidatorUtil validator;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, Gson gson, ModelMapper mapper, ValidatorUtil validator) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return bookRepository.count() > 1;
    }

    @Override
    public String readBooksFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(BOOKS_JSON_PATH)));
    }

    @Override
    public String importBooks() throws IOException {
        StringBuilder builder = new StringBuilder();
        BookImportDto[] bookImportDto = gson.fromJson(this.readBooksFileContent(), BookImportDto[].class);

        for (BookImportDto bookDto : bookImportDto) {

            Optional<Author> byId = authorRepository.findById(bookDto.getAuthor());
            if (validator.isValid(bookDto) && byId.isPresent()) {
                builder.append(String.format("Successfully imported Book: %s written in %s",
                        bookDto.getName(),
                        bookDto.getWritten())).append(System.lineSeparator());

                Book book = mapper.map(bookDto, Book.class);
                book.setAuthor(byId.get());
                bookRepository.saveAndFlush(book);
            } else {
                builder.append("Invalid Book").append(System.lineSeparator());
            }
        }

        return builder.toString();
    }
}
