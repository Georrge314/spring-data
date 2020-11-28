package softuni.library.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.library.models.dto.xmls.LibraryImportDto;
import softuni.library.models.dto.xmls.LibraryImportRootDto;
import softuni.library.models.entities.Book;
import softuni.library.models.entities.Library;
import softuni.library.repositories.BookRepository;
import softuni.library.repositories.LibraryRepository;
import softuni.library.services.LibraryService;
import softuni.library.util.validator.ValidatorUtil;
import softuni.library.util.parser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

@Service
public class LibraryServiceImpl implements LibraryService {
    public static final String LIBRARIES_XML_PATH = "src/main/resources/files/xml/libraries.xml";

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final XmlParser xmlParser;
    private final ValidatorUtil validator;
    private final ModelMapper mapper;

    public LibraryServiceImpl(LibraryRepository libraryRepository, BookRepository bookRepository, XmlParser xmlParser, ValidatorUtil validator, ModelMapper mapper) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
        this.xmlParser = xmlParser;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return libraryRepository.count() > 1;
    }

    @Override
    public String readLibrariesFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(LIBRARIES_XML_PATH)));
    }

    @Override
    public String importLibraries() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();
        LibraryImportRootDto libraryExportRootDto = xmlParser.parserXml(LibraryImportRootDto.class, LIBRARIES_XML_PATH);

        for (LibraryImportDto libraryDto : libraryExportRootDto.getLibraries()) {

            Optional<Library> byName = libraryRepository.findByName(libraryDto.getName());
            Optional<Book> bookById = bookRepository.findById(libraryDto.getBook().getId());
            if (byName.isEmpty() && bookById.isPresent() && validator.isValid(libraryDto)) {
                builder.append(String.format("Successfully added Library: %s - %s",
                        libraryDto.getName(), libraryDto.getLocation()))
                        .append(System.lineSeparator());
                Library library = mapper.map(libraryDto, Library.class);
                library.setBooks(Set.of(bookById.get()));

                libraryRepository.saveAndFlush(library);
            } else {
                builder.append("Invalid Library").append(System.lineSeparator());
            }
        }

        return builder.toString();
    }
}
