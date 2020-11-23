package spring.course.demo.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.course.demo.domain.dtos.export.otherDto.CategoryExportDto;
import spring.course.demo.domain.dtos.seed.CategorySeedDto;
import spring.course.demo.domain.entity.Category;
import spring.course.demo.domain.repositories.CategoryRepository;
import spring.course.demo.domain.repositories.ProductRepository;
import spring.course.demo.service.CategoryService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;
    private final Gson gson;
    private final ModelMapper mapper;
    public static final String CATEGORY_PATH = "src/main/resources/jsons/categories.json";

    public CategoryServiceImpl(CategoryRepository categoryRepo, ProductRepository productRepo, Gson gson, ModelMapper mapper) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void seedCategory() throws IOException {
        //read JSON file;
        String content = String.join("", Files.readAllLines(Path.of(CATEGORY_PATH)));
        //Parse JSON script
        CategorySeedDto[] dto = gson.fromJson(content, CategorySeedDto[].class);
        //Mapping dto to object
        TypeMap<CategorySeedDto, Category> typeMap = mapper.createTypeMap(CategorySeedDto.class, Category.class);
        List<Category> categories = Arrays.stream(dto).map(typeMap::map).collect(Collectors.toList());
        //seed categories
        categories.forEach(categoryRepo::save);
    }

    @Override
    public String categoriesByProducts() {
        List<Category> categories = categoryRepo.findAll().stream()
                .sorted(Comparator.comparingInt(c -> c.getProducts().size()))
                .collect(Collectors.toList());

        List<CategoryExportDto> exportDtos = categories.stream().map(c -> {
            CategoryExportDto exportDto = mapper.map(c, CategoryExportDto.class);

            exportDto.setCategory(c.getName());
            exportDto.setProductCount(c.getProducts().size());
            double average = c.getProducts().stream().mapToDouble(p -> p.getPrice().doubleValue()).average().orElse(0);
            double sum = c.getProducts().stream().mapToDouble(p -> p.getPrice().doubleValue()).sum();
            exportDto.setAveragePrice(BigDecimal.valueOf(average).setScale(6, RoundingMode.DOWN));
            exportDto.setTotalRevenue(BigDecimal.valueOf(sum).setScale(2, RoundingMode.DOWN));

            return exportDto;
        }).collect(Collectors.toList());


        return gson.toJson(exportDtos.toArray());
    }
}
