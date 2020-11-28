package softuni.library.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.library.models.dto.xmls.CharacterImportDto;
import softuni.library.models.dto.xmls.CharacterImportRootDto;
import softuni.library.models.entities.Book;
import softuni.library.models.entities.Character;
import softuni.library.repositories.BookRepository;
import softuni.library.repositories.CharacterRepository;
import softuni.library.services.CharacterService;
import softuni.library.util.validator.ValidatorUtil;
import softuni.library.util.parser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

@Service
public class CharacterServiceImpl implements CharacterService {
    public static final String CHARACTERS_XML_PATH = "src/main/resources/files/xml/characters.xml";

    private final CharacterRepository characterRepository;
    private final BookRepository bookRepository;
    private final XmlParser xmlParser;
    private final ValidatorUtil validator;
    private final ModelMapper mapper;

    public CharacterServiceImpl(CharacterRepository characterRepository,
                                BookRepository bookRepository, XmlParser xmlParser, ValidatorUtil validator, ModelMapper mapper) {
        this.characterRepository = characterRepository;
        this.bookRepository = bookRepository;
        this.xmlParser = xmlParser;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return characterRepository.count() > 1;
    }

    @Override
    public String readCharactersFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(CHARACTERS_XML_PATH)));
    }

    @Override
    public String importCharacters() throws JAXBException {
        StringBuilder builder = new StringBuilder();
        CharacterImportRootDto characterImportRootDto = xmlParser.parserXml(CharacterImportRootDto.class, CHARACTERS_XML_PATH);

        for (CharacterImportDto characterDto : characterImportRootDto.getCharacters()) {

            Optional<Book> bookById = bookRepository.findById(characterDto.getBook().getId());
            if (bookById.isPresent() && validator.isValid(characterDto) && characterDto.getMiddleName().length() == 1) {
                builder.append(String.format("Successfully added Character: %s %s %s born in %s",
                        characterDto.getFirstName(), characterDto.getMiddleName(), characterDto.getLastName(),
                        characterDto.getBirthday())).append(System.lineSeparator());

                Character character = mapper.map(characterDto, Character.class);
                character.setBook(bookById.get());
                characterRepository.saveAndFlush(character);

            } else {
                builder.append("Invalid Character").append(System.lineSeparator());
            }
        }

        return builder.toString();
    }

    @Override
    public String findCharactersInBookOrderedByLastNameDescendingThenByAge() {
        StringBuilder builder = new StringBuilder();
        Set<Character> characters = characterRepository.findAllByAgeIsBiggerOrEqual32Ordered();

        for (Character character : characters) {
            builder.append(String.format("Character name %s %s %s, age %d, in book %s",
                    character.getFirstName(), character.getMiddleName(), character.getLastName(),
                    character.getAge(), character.getBook().getName())).append(System.lineSeparator());
        }

        return builder.toString();
    }
}
