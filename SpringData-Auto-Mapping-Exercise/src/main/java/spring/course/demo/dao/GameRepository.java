package spring.course.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.course.demo.entities.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findById(long id);

    Game findByTitle(String title);
}
