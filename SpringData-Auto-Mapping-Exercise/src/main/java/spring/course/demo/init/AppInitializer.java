package spring.course.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.course.demo.service.GameService;
import spring.course.demo.service.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class AppInitializer implements CommandLineRunner {
    private final UserService userService;
    private final GameService gameService;
    private final BufferedReader reader;

    @Autowired
    public AppInitializer(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.reader = new BufferedReader(new InputStreamReader(System.in));

    }

    @Override
    public void run(String... args) throws Exception {
        String input = reader.readLine();
        while (!input.equals("")) {
            String[] tokens = input.split("\\|");
            switch (tokens[0]) {
                case "RegisterUser":
                    String registerText = userService.registerUser(tokens[1], tokens[2], tokens[3], tokens[4]);
                    System.out.println(registerText);
                    userService.setAdministration();
                    break;
                case "LoginUser":
                    String loginText = userService.loginUser(tokens[1], tokens[2]);
                    System.out.println(loginText);
                    break;
                case "Logout":
                    String logoutText = userService.logout(tokens[1]);
                    System.out.println(logoutText);
                    break;
                case "AddGame":
                    String addGameText = gameService.addGame(tokens[1], Double.parseDouble(tokens[2]),
                            Double.parseDouble(tokens[3]),
                            tokens[4], tokens[5], tokens[6], tokens[7]);
                    System.out.println(addGameText);
                    break;
                case "EditGame":
                    String values = Arrays.stream(tokens).skip(2).collect(Collectors.joining("|"));
                    String updateGameText = gameService.editGame(Integer.parseInt(tokens[1]), values);
                    System.out.println(updateGameText);
                    break;
                case "DeleteGame":
                    String deleteGameText = gameService.deleteGame(Integer.parseInt(tokens[1]));
                    System.out.println(deleteGameText);
                    break;
                case "AllGames":
                    gameService.printTitleAndPriceOfAllGames();
                    break;
                case "DetailGame":
                    gameService.printDetailsOfGame(tokens[1]);
                    break;
            }
            input = reader.readLine();
        }
    }
}
