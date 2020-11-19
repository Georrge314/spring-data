package spring.course.demo.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.course.demo.dao.GameRepository;
import spring.course.demo.dto.DetailGameDto;
import spring.course.demo.dto.GameDto;
import spring.course.demo.entities.Game;
import spring.course.demo.service.GameService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepo;

    @Autowired
    public GameServiceImpl(GameRepository gameRepo) {
        this.gameRepo = gameRepo;
    }


    @Override
    @Transactional
    public String addGame(String title, double price, double size, String trailerUrl,
                          String thumbnailUrl, String description, String releaseDate) {
        Game game = new Game();
        if (validateTitle(title)) {
            game.setTitle(title);
        } else {
            return "Invalid title";
        }

        if (price > 0) {
            game.setPrice(price);
        } else {
            return "Price must be a positive number";
        }

        if (size > 0) {
            game.setSize(size);
        } else {
            return "Size must be a positive number";
        }

        if (validateTrailerUrl(trailerUrl)) {
            String trailerId = trailerUrl.substring(trailerUrl.length() - 11);
            game.setTrailer(trailerId);
        } else {
            return "Invalid trailer Url";
        }

        if (validateThumbnailUrl(thumbnailUrl)) {
            game.setImage(thumbnailUrl);
        } else {
            return "Invalid Thumbnail Url";
        }

        if (description.length() >= 20) {
            game.setDescription(description);
        } else {
            return "Description must be at least 20 symbols";
        }

        int day = Integer.parseInt(releaseDate.substring(0, 2));
        int month = Integer.parseInt(releaseDate.substring(3, 5));
        int year = Integer.parseInt(releaseDate.substring(6));
        LocalDate date = LocalDate.of(year, month, day);
        game.setReleaseDate(date);

        gameRepo.save(game);

        return "Added " + title;
    }

    @Override
    @Transactional
    public String editGame(int id, String values) {
        Game game = gameRepo.findById(id);
        if (game == null) {
            return "Game does not exists.";
        }
        String[] commands = values.split("\\|");
        for (String command : commands) {

            int beginIndex = command.indexOf("=") + 1;
            if (command.startsWith("title")) {
                String newTitle = command.substring(beginIndex);
                game.setTitle(newTitle);
            } else if (command.startsWith("price")) {
                double newPrice = Double.parseDouble(command.substring(beginIndex));
                game.setPrice(newPrice);
            } else if (command.startsWith("size")) {
                double newSize = Double.parseDouble(command.substring(beginIndex));
                game.setSize(newSize);
            } else if (command.startsWith("trailer id")) {
                String newTrailerId = command.substring(beginIndex);
                game.setTrailer(newTrailerId);
            } else if (command.startsWith("thumbnail URL")) {
                String newThumbnailUrl = command.substring(beginIndex);
                game.setImage(newThumbnailUrl);
            } else if (command.startsWith("description")) {
                String newDescription = command.substring(beginIndex);
                game.setDescription(newDescription);
            } else if (command.startsWith("release date")) {
                String givenDate = command.substring(beginIndex);
                int year = Integer.parseInt(givenDate.substring(6));
                int month = Integer.parseInt(givenDate.substring(3, 5));
                int day = Integer.parseInt(givenDate.substring(0, 2));
                LocalDate date = LocalDate.of(year, month, day);
                game.setReleaseDate(date);
            }
        }

        gameRepo.save(game);
        return "Edited " + game.getTitle();
    }

    @Override
    @Transactional
    public String deleteGame(int id) {
        Game game = gameRepo.findById(id);
        if (game == null) {
            return "Game does not exists.";
        }

        gameRepo.delete(game);
        return "Deleted " + game.getTitle();
    }

    @Override
    public void printTitleAndPriceOfAllGames() {
        ModelMapper mapper = new ModelMapper();
        TypeMap<Game, GameDto> managerTypeMap = mapper.createTypeMap(Game.class, GameDto.class);
        mapper.validate();
        List<Game> games = gameRepo.findAll();
        List<GameDto> gamesDto = games.stream().map(managerTypeMap::map).collect(Collectors.toList());
        gamesDto.forEach(System.out::println);
    }

    @Override
    public void printDetailsOfGame(String title) {
        ModelMapper mapper = new ModelMapper();
        TypeMap<Game, DetailGameDto> managerTypeMap = mapper.createTypeMap(Game.class, DetailGameDto.class);
        mapper.validate();

        Game game = gameRepo.findByTitle(title);
        DetailGameDto dto = managerTypeMap.map(game);
        System.out.println(dto);
    }

    @Override
    public void setUserToGame() {

    }

    private boolean validateTitle(String title) {
        return Character.isUpperCase(title.charAt(0)) && title.length() >= 3 && title.length() <= 100;
    }

    private boolean validateTrailerUrl(String trailerUrl) {
        int charLength = "https://www.youtube.com/watch?v=12345678910".length();
        return trailerUrl.contains("https://www.youtube.com/watch?v=") && trailerUrl.length() == charLength;
    }

    private boolean validateThumbnailUrl(String thumbnailUrl) {
        return thumbnailUrl.startsWith("http://") || thumbnailUrl.startsWith("https://");
    }
}
