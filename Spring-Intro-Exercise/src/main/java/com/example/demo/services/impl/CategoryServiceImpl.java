package com.example.demo.services.impl;

import com.example.demo.constants.GlobalConstants;
import com.example.demo.entities.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.CategoryService;
import com.example.demo.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.constants.GlobalConstants.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        String[] fileContent = fileUtil.readFileContent(CATEGORIES_PATH);

        Arrays.stream(fileContent).forEach(row -> {
            List<Category> categories = categoryRepository.findAll();
            boolean isUnique = true;
            for (Category category : categories) {
                if (row.equals(category.getName())) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                Category category = new Category(row);
                categoryRepository.saveAndFlush(category);
            }
        });


    }

    @Override
    public Category getCategoryById(int id) {
       return categoryRepository.getOne((long) id);
    }
}
