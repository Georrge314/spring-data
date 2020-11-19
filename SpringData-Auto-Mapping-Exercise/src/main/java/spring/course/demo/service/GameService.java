package spring.course.demo.service;

public interface GameService {
    String addGame(String title, double price, double size, String trailerUrl,
                   String thumbnailURL, String description, String releaseDate);

    String editGame(int id, String values);

    String deleteGame(int id);

    void printTitleAndPriceOfAllGames();

    void printDetailsOfGame(String title);

    void setUserToGame();
}
