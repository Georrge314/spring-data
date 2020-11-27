package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.imports.offersImportDto.OfferImportDto;
import softuni.exam.models.dtos.imports.offersImportDto.OfferImportRootDto;
import softuni.exam.models.entities.Offer;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.parser.XmlParser;
import softuni.exam.util.validator.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {
    public static final String OFFERS_PATH = "src/main/resources/files/xml/offers.xml";

    private final OfferRepository offerRepository;
    private final CarRepository carRepository;
    private final SellerRepository sellerRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validation;
    private final XmlParser xmlParser;

    public OfferServiceImpl(OfferRepository offerRepository, CarRepository carRepository, SellerRepository sellerRepository, ModelMapper mapper, ValidationUtil validation, XmlParser xmlParser) {
        this.offerRepository = offerRepository;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
        this.mapper = mapper;
        this.validation = validation;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 1;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(OFFERS_PATH)));
    }

    @Override
    @Transactional
    public String importOffers() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();
        OfferImportRootDto offerImportRootDto = xmlParser.parserXml(OfferImportRootDto.class, OFFERS_PATH);

        for (OfferImportDto offerImportDtp : offerImportRootDto.getOffers()) {

            Optional<Offer> isOfferExists = offerRepository
                    .findByDescriptionAndAddedOn(offerImportDtp.getDescription(), offerImportDtp.getAddedOn());
            if (isOfferExists.isEmpty() && validation.isValid(offerImportDtp)) {
                builder.append(String.format("Successfully import offer %s - %s",
                        offerImportDtp.getAddedOn(),
                        offerImportDtp.isHasGoldStatus())).append(System.lineSeparator());

                Offer offer = mapper.map(offerImportDtp, Offer.class);
                offer.setCar(carRepository.findById(offerImportDtp.getCar().getId()).orElse(null));
                offer.setSeller(sellerRepository.findById(offerImportDtp.getSeller().getId()).orElse(null));
                offerRepository.saveAndFlush(offer);
            } else {
                builder.append("invalid offer").append(System.lineSeparator());
            }
        }


        return builder.toString();
    }
}
