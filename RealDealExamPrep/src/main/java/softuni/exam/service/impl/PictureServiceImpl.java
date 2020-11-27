package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.imports.PictureImportDto;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.validator.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PictureServiceImpl implements PictureService {
    public static final String PICTURE_PATH = "src/main/resources/files/json/pictures.json";

    private final PictureRepository pictureRepository;
    private final CarRepository carRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validation;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, CarRepository carRepository, Gson gson, ModelMapper mapper, ValidationUtil validation) {
        this.pictureRepository = pictureRepository;
        this.carRepository = carRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validation = validation;
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 1;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return String.join("", Files.readAllLines(Path.of(PICTURE_PATH)));
    }

    @Override
    @Transactional
    public String importPictures() throws IOException {
        StringBuilder builder = new StringBuilder();
        PictureImportDto[] allPictureImportDto = gson.fromJson(this.readPicturesFromFile(), PictureImportDto[].class);

        for (PictureImportDto pictureImportDto : allPictureImportDto) {
            Optional<Picture> isPictureExist = pictureRepository.findByName(pictureImportDto.getName());
            if (isPictureExist.isEmpty() && validation.isValid(pictureImportDto)) {
                builder.append(String.format("Successfully import picture - %s",pictureImportDto.getName()));
                builder.append(System.lineSeparator());

                Picture picture = mapper.map(pictureImportDto, Picture.class);
                picture.setCar(carRepository.findById(pictureImportDto.getCar()).orElse(null));
                pictureRepository.saveAndFlush(picture);
            } else {
                builder.append("Invalid picture").append(System.lineSeparator());
            }
        }

        return builder.toString();
    }
}
