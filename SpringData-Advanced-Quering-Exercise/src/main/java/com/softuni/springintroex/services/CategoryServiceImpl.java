package com.softuni.springintroex.services;

import com.softuni.springintroex.constants.GlobalConstants;
import com.softuni.springintroex.entities.Category;
import com.softuni.springintroex.repositories.CategoryRepo;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.softuni.springintroex.constants.GlobalConstants.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo, FileUtil fileUtil) {
        this.categoryRepo = categoryRepo;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        String[] fileContent = fileUtil.readFileContent(CATEGORIES_FILE_PATH);

        Arrays.stream(fileContent).forEach(row -> {
            List<Category> categories = categoryRepo.findAll();
            boolean isUnique = true;
            for (Category category : categories) {
                if (row.equals(category.getName())) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                Category category = new Category(row);
                categoryRepo.saveAndFlush(category);
            }
        });
    }

    @Override
    public Category getCategoryById(int id) {
      return  categoryRepo.findCategoryById((long)id);
    }
}
