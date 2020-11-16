package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.AgeRestriction;
import com.softuni.springintroex.entities.Book;
import com.softuni.springintroex.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    List<Book> findBookByAgeRestriction(AgeRestriction age);

    List<Book> findBookByEditionTypeAndCopiesIsLessThan(EditionType editionType, int copies);

    List<Book> findBookByPriceLessThanOrPriceGreaterThan(BigDecimal lowerBound, BigDecimal upperBound);

    @Query("SELECT b FROM Book b WHERE substring(b.releaseDate, 0, 4) not like :year")
    Set<Book> findBooksByReleaseDateNotInYear(String year);

    Set<Book> findBooksByReleaseDateBefore(LocalDate date);

    // case doesn't matter;
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE :pattern")
    Set<Book> findBooksByTitleContaining(String pattern);

    // case doesn't matter
    @Query("SELECT b FROM Book b WHERE b.author.lastName LIKE :pattern")
    Set<Book> findBooksByAuthorLastNameEndWith(String pattern);

    @Query("SELECT COUNT(b.id) FROM Book b WHERE length(b.title) > ?1")
    int getCountOfBooksWhereTitleLengthIsLongerThan(int number);

    Book findBookByTitle(String title);
}
