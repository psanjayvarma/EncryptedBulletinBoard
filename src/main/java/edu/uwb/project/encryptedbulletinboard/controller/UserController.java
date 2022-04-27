package edu.uwb.project.encryptedbulletinboard.controller;

import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import edu.uwb.project.encryptedbulletinboard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new UserModel());
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new UserModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserModel userModel, Model model){
        System.out.println("Register request for ...." + userModel);
        UserModel registeredUser = userService.registerUser(userModel.getLogin(), userModel.getPassword(), userModel.getEmail());
        if(registeredUser == null){
            model.addAttribute("registrationError", "User Already Exists");
            return "register_page";
        }
        model.addAttribute("userRegistered","New User Registered Successfully");
        return "login_page";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserModel userModel, Model model){
        System.out.println("Register request for ...." + userModel);
        UserModel authenticated = userService.authenticate(userModel.getLogin(), userModel.getPassword());
        if(authenticated != null){
            model.addAttribute("userLogin", authenticated.getLogin());
            return "welcome_page";
        }
        else{
            return "error_page";
        }
    }

}
