package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findCategoryById(Long id);
}
