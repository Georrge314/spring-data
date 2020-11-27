package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dtos.imports.CarImportDto;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.validator.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CarServiceImpl implements CarService {
    public static final String CARS_PATH = "src/main/resources/files/json/cars.json";

    private final CarRepository carRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validation;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper mapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validation = validationUtil;
    }

    @Override
    public boolean areImported() {
        return carRepository.count() > 1;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(CARS_PATH)));
    }

    @Override
    @Transactional
    public String importCars() throws IOException {
        StringBuilder builder = new StringBuilder();
        CarImportDto[] allCarImportDto = gson.fromJson(readCarsFileContent(), CarImportDto[].class);

        for (CarImportDto carImportDto : allCarImportDto) {
            Optional<Car> isCarExists = carRepository
                    .findByMakeAndModelAndKilometers(carImportDto.getMake(), carImportDto.getModel(), carImportDto.getKilometers());

            if (isCarExists.isEmpty() && validation.isValid(carImportDto)) {
                builder.append(String.format("Successfully imported car - %s - %s",
                        carImportDto.getMake(),
                        carImportDto.getModel())).append(System.lineSeparator());

                carRepository.saveAndFlush(mapper.map(carImportDto, Car.class));
            } else {
                builder.append("Invalid car").append(System.lineSeparator());
            }

        }
        return builder.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder builder = new StringBuilder();
        if (this.areImported()) {
            carRepository.findAllCarsOrderByPictureSizeDescThenByMake()
                    .forEach(car -> builder.append(car).append(System.lineSeparator()));
            return builder.toString();
        }
        return null;
    }
}
