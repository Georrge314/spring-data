package spring.course.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.course.demo.dao.LoginUserRepository;
import spring.course.demo.dao.UserRepository;
import spring.course.demo.entities.LoginUser;
import spring.course.demo.entities.User;
import spring.course.demo.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final LoginUserRepository loginUserRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, LoginUserRepository loginUserRepo) {
        this.userRepo = userRepo;
        this.loginUserRepo = loginUserRepo;
    }


    @Override
    @Transactional
    public String registerUser(String email, String password, String confirmPass, String fullName) {
         if (!isEmailValid(email)) {
             return "Incorrect email.";
         }
         if (!isPasswordValid(password, confirmPass)) {
             return "Incorrect password";
         }
         User user = new User(email,password,fullName);
         userRepo.save(user);
         return String.format("%s was registered",fullName);
    }

    @Override
    @Transactional
    public String loginUser(String email, String password) {
        User user = userRepo.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            if (loginUserRepo.findByUser(user) == null) {
                LoginUser loginUser = new LoginUser(user);
                loginUserRepo.save(loginUser);
                return String.format("Successfully logged in %s", user.getFullName());
            } else {
                return "User already logged in";
            }
        }
        return "Incorrect username / password";
    }

    @Override
    @Transactional
    public String logout(String email) {
        if (loginUserRepo.count() > 0) {
            LoginUser loginUser = loginUserRepo.findByUserEmail(email);
            if (loginUser != null) {
                loginUserRepo.delete(loginUser);
                return String.format("User %s successfully logged out",loginUser.getUser().getFullName());
            }
        }
        return "Cannot log out. No user was logged in.";
    }

    @Override
    @Transactional
    public void setAdministration() {
        User user = userRepo.findById(1);
        user.setAdministrator(true);
        userRepo.save(user);
    }

    private boolean isPasswordValid(String password, String confirmPass) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches() && password.equals(confirmPass);
    }

    private boolean isEmailValid(String email) {
        if (email.contains("@")) {
            User user = userRepo.findByEmail(email);
            return user == null;
        }
        return false;
    }


}
