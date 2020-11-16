package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {
    Author findAuthorById(Long id);

    Set<Author> findAuthorsByFirstNameEndingWith(String pattern);

    Set<Author> findAllBy();
}
