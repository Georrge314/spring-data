package softuni.library.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.library.models.dto.jsons.AuthorImportDto;
import softuni.library.models.entities.Author;
import softuni.library.repositories.AuthorRepository;
import softuni.library.services.AuthorService;
import softuni.library.util.validator.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    public static final String AUTHORS_JSON_PATH = "src/main/resources/files/json/authors.json";

    private final AuthorRepository authorRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidatorUtil validator;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, Gson gson, ModelMapper mapper, ValidatorUtil validator) {
        this.authorRepository = authorRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return authorRepository.count() > 1;
    }

    @Override
    public String readAuthorsFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(AUTHORS_JSON_PATH)));
    }

    @Override
    public String importAuthors() throws IOException {
        StringBuilder builder = new StringBuilder();
        AuthorImportDto[] authorImportDto = gson.fromJson(this.readAuthorsFileContent(), AuthorImportDto[].class);

        for (AuthorImportDto authorDto : authorImportDto) {

            Optional<Author> byFirstName = authorRepository.findByFirstName(authorDto.getFirstName());
            Optional<Author> byLastName = authorRepository.findByLastName(authorDto.getLastName());
            if (byFirstName.isEmpty() && byLastName.isEmpty() && validator.isValid(authorDto)) {
                builder.append(String.format("Successfully imported Author: %s %s - %s",
                        authorDto.getFirstName(),authorDto.getLastName(),authorDto.getBirthTown()))
                        .append(System.lineSeparator());

                authorRepository.saveAndFlush(mapper.map(authorDto, Author.class));
            } else {
                builder.append("Invalid Author").append(System.lineSeparator());
            }
        }

        return builder.toString();
    }
}
