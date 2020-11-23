package spring.course.demo.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.course.demo.domain.dtos.export.otherDto.ProductSellerExportDto;
import spring.course.demo.domain.dtos.seed.ProductSeedDto;
import spring.course.demo.domain.entity.Category;
import spring.course.demo.domain.entity.Product;
import spring.course.demo.domain.entity.User;
import spring.course.demo.domain.repositories.CategoryRepository;
import spring.course.demo.domain.repositories.ProductRepository;
import spring.course.demo.domain.repositories.UserRepository;
import spring.course.demo.service.ProductService;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;
    private final Gson gson;
    private final ModelMapper mapper;
    public static final String PRODUCT_PATH = "src/main/resources/jsons/products.json";

    @Autowired
    public ProductServiceImpl(ProductRepository productRepo, UserRepository userRepo, CategoryRepository categoryRepo, Gson gson, ModelMapper mapper) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void seedProducts() throws IOException {
        // read JSON Content
        String content = String.join("", Files.readAllLines(Path.of(PRODUCT_PATH)));
        // parse from Json format to object
        ProductSeedDto[] dto = gson.fromJson(content, ProductSeedDto[].class);
        //map dto to object
        TypeMap<ProductSeedDto, Product> typeMapManager = mapper.createTypeMap(ProductSeedDto.class, Product.class);
        List<Product> products = Arrays.stream(dto).map(typeMapManager::map).collect(Collectors.toList());
        //set to some products seller and buyer
        Random random = new Random();
        for (int i = 0; i < products.size(); i += 8) {
            Product product = products.get(i);
            User buyer = userRepo.findById(random.nextInt((int) (userRepo.count() / 2)) + 1);
            product.setBuyer(buyer);
        }
        for (int i = 0; i < products.size(); i += 4) {
            Product product = products.get(i);
            User seller = userRepo.findById(random.nextInt((int) (userRepo.count() / 2)) + 1 + userRepo.count() / 2);
            product.setSeller(seller);
        }
        //seed products to DB
        products.forEach(productRepo::save);
    }

    @Override
    @Transactional
    public void generateCategory() {
        List<Category> categories = categoryRepo.findAll();
        List<Product> products = productRepo.findAll();

        Random random = new Random();
        for (Product product : products) {
            int indexOne = random.nextInt(categories.size() / 2);
            int indexTwo = random.nextInt(categories.size() / 2 + 1) + categories.size() / 2;
            product.getCategories().add(categories.get(indexOne));
            product.getCategories().add(categories.get(indexTwo));
        }

        products.forEach(productRepo::save);
    }

    @Override
    public String getAllProductsInRange() {

        return gson.toJson(productRepo
                .findAllByBuyerIsNullAndPriceBetweenOrderByPrice(BigDecimal.valueOf(500), BigDecimal.valueOf(1000))
                .stream()
                .map(p -> {
                    ProductSellerExportDto exportDto = this.mapper.map(p, ProductSellerExportDto.class);

                    try {
                        p.getSeller();
                        String firstName = p.getSeller().getFirstName();
                        String lastName = p.getSeller().getLastName();
                        exportDto.setSeller(String.format("%s %s", firstName, lastName));
                    } catch (NullPointerException ignored) {

                    }

                    return exportDto;
                }).toArray());
    }
}
