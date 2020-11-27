package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.imports.SellerImportDto;
import softuni.exam.models.dtos.imports.SellerImportRootDto;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.parser.XmlParser;
import softuni.exam.util.validator.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SellerServiceImpl implements SellerService {
    public static final String SELLERS_PATH = "src/main/resources/files/xml/sellers.xml";

    private final SellerRepository sellerRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validation;
    private final XmlParser xmlParser;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper mapper, ValidationUtil validation, XmlParser xmlParser) {
        this.sellerRepository = sellerRepository;
        this.mapper = mapper;
        this.validation = validation;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return sellerRepository.count() > 1;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return String.join("", Files.readAllLines(Path.of(SELLERS_PATH)));
    }

    @Override
    @Transactional
    public String importSellers() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();
        SellerImportRootDto sellerImportRootDto = xmlParser.parserXml(SellerImportRootDto.class, SELLERS_PATH);

        for (SellerImportDto sellerDto : sellerImportRootDto.getSellers()) {
            Optional<Seller> isSellerExists = sellerRepository.findByEmail(sellerDto.getEmail());

            if (isSellerExists.isEmpty() && validation.isValid(sellerDto)) {
                builder.append(String.format("Successfully import seller %s - %s",
                        sellerDto.getLastName(),
                        sellerDto.getEmail()));
                builder.append(System.lineSeparator());

                sellerRepository.saveAndFlush(mapper.map(sellerDto, Seller.class));
            } else {
                builder.append("invalid seller").append(System.lineSeparator());
            }
        }

        return builder.toString();
    }
}
