package spring.course.demo.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.course.demo.domain.dtos.export.otherDto.ProductBuyerExportDto;
import spring.course.demo.domain.dtos.export.otherDto.UserExportDto;
import spring.course.demo.domain.dtos.export.usersProducts.ProductExportDto;
import spring.course.demo.domain.dtos.export.usersProducts.UserDto;
import spring.course.demo.domain.dtos.export.usersProducts.UsersAndCountDto;
import spring.course.demo.domain.dtos.seed.UserSeedDto;
import spring.course.demo.domain.entity.Product;
import spring.course.demo.domain.entity.User;
import spring.course.demo.domain.repositories.UserRepository;
import spring.course.demo.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final ModelMapper mapper;
    private final Gson gson;
    private static final String USERS_PATH = "src/main/resources/jsons/users.json";

    @Autowired
    public UserServiceImpl(UserRepository userRepo, ModelMapper mapper, Gson gson) {
        this.userRepo = userRepo;
        this.mapper = mapper;
        this.gson = gson;
    }

    @Override
    @Transactional
    public void seedUsers() throws IOException {
        //Read JSON
        String content = String.join("", Files.readAllLines(Path.of(USERS_PATH)));
        //from JSON to DTO
        UserSeedDto[] dto = this.gson.fromJson(content, UserSeedDto[].class);
        //seed to db
        TypeMap<UserSeedDto, User> typeMapManager = mapper.createTypeMap(UserSeedDto.class, User.class);
        List<User> users = Arrays.stream(dto).map(typeMapManager::map).collect(Collectors.toList());
        users.forEach(userRepo::save);
    }

    @Override
    public String getUsersSoldProducts() {
        Set<User> users = userRepo.findAllUsersWithSoldProductsGreaterThanOne();

        List<UserExportDto> dtos = users.stream().map(u -> {
            UserExportDto userExportDto = mapper.map(u, UserExportDto.class);
            List<String> names = new ArrayList<>();

            Set<Product> products = u.getSoldProducts();
            for (Product product : products) {
                try {
                    names.add(product.getBuyer().getFirstName());
                } catch (NullPointerException exception) {
                    names.add("null");
                }

                try {
                    names.add(product.getBuyer().getLastName());
                } catch (NullPointerException exception) {
                    names.add("null");
                }
            }

            Set<ProductBuyerExportDto> dtoProducts = userExportDto.getSoldProducts();
            for (ProductBuyerExportDto productDto : dtoProducts) {
                productDto.setBuyerFirstName(names.remove(0));
                productDto.setBuyerLastName(names.remove(0));
            }

            return userExportDto;
        }).collect(Collectors.toList());

        dtos.forEach(d -> {
            d.getSoldProducts().removeIf(p -> p.getBuyerLastName().equals("null"));
        });

        return gson.toJson(dtos.toArray());
    }

    @Override
    public String getUsersAndProducts() {
        UsersAndCountDto usersAndCountDto = new UsersAndCountDto();

        Set<UserDto> usersDto = userRepo.findAllUsersWithSoldProductsGreaterThanOneTwo()
                .stream().map(user -> {
                    UserDto userDto = mapper.map(user, UserDto.class);

                    Set<ProductExportDto> productDto = user.getSoldProducts()
                            .stream().map(p -> mapper.map(p, ProductExportDto.class))
                            .collect(Collectors.toSet());

                    userDto.getSoldProducts().setCount(productDto.size());
                    userDto.getSoldProducts().setProducts(productDto);


                    return userDto;
                }).collect(Collectors.toSet());

        usersAndCountDto.setUsersCount(usersDto.size());
        usersAndCountDto.setUsers(usersDto);

        return gson.toJson(usersAndCountDto);
    }
}
