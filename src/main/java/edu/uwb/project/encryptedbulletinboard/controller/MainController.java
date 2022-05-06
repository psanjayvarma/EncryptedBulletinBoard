package edu.uwb.project.encryptedbulletinboard.controller;

import edu.uwb.project.encryptedbulletinboard.model.BoardModel;
import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import edu.uwb.project.encryptedbulletinboard.service.BoardService;
import edu.uwb.project.encryptedbulletinboard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    UserModel authenticated = new UserModel();

    private final UserService userService;
    private final BoardService boardService;

    public MainController(UserService userService, BoardService boardService) {
        this.userService = userService;
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String getIndexPage(Model model){
        if(authenticated.getLogin() == null){
            return getLoginPage(model);
        }
        model.addAttribute("user", authenticated);
        return "welcome_page";
    }

    @GetMapping("/welcome")
    public String getHomePage(Model model){
        model.addAttribute("user", authenticated);
        return "welcome_page";
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

    @GetMapping("/createboard")
    public String getCreateBoardPage(Model model){
        model.addAttribute("board", new BoardModel());
        return "create_board";
    }

    @GetMapping("/joinboard")
    public String getJoinBoardPage(Model model){
        model.addAttribute("Id", 0);
        return "join_board";
    }

    @GetMapping("/signout")
    public String getSingOutPage(Model model){
        authenticated = new UserModel();
        return "redirect:/";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserModel userModel, Model model){
        System.out.println("Register request for ...." + userModel);
        UserModel registeredUser = userService.registerUser(userModel.getLogin(), userModel.getPassword(), userModel.getEmail());
        if(registeredUser == null){
            model.addAttribute("registrationError", "User Already Exists");
            return "register_page";
        }
        model.addAttribute("userRegistered","New User Registered Successfully. Login with using Credentials");
        return "login_page";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserModel userModel, Model model){
        System.out.println("Register request for ...." + userModel);
        authenticated = userService.authenticate(userModel.getLogin(), userModel.getPassword());
        if(authenticated != null){
            model.addAttribute("user", authenticated);
            return "welcome_page";
        }
        else{
            model.addAttribute("loginError", "Invalid Login Credentials");
            return "login_page";
        }
    }

    @PostMapping("/createboard")
    public String createNewBoard(@ModelAttribute UserModel userModel, BoardModel boardModel, Model model){
        BoardModel newBoard = boardService.createNewBoard(boardModel.getName());
        System.out.println("New Board Creation request for ...." + newBoard +"by user "+ authenticated);
        authenticated = userService.addNewBoard(authenticated, newBoard);
        model.addAttribute("user", authenticated);
        return "welcome_page";
    }


    @PostMapping("/joinboard")
    public String joinBoard(@ModelAttribute UserModel userModel, Integer Id, Model model){
        BoardModel board = boardService.getBoard(Id);
        System.out.println("Join Request for ......" + board + " by " + authenticated);
        if(board == null){
            System.out.println("Board ID is not valid");
            model.addAttribute("JoinError", "Board Doesn't Exist");
            return "join_board";
        }
        if(userService.hasTheBoard(authenticated,Id)){
            System.out.println("User is already in the board");
            model.addAttribute("JoinError", "You already joined the Board");
            return "join_board";
        }
        authenticated = userService.addNewBoard(authenticated, board);
        model.addAttribute("user", authenticated);
        return "welcome_page";
    }

    @GetMapping("/board/{board_id}")
    public String viewBoardPage(@PathVariable("board_id") Integer Id, Model model){
        BoardModel boardModel = boardService.getBoard(Id);
        model.addAttribute("board", boardModel);
        return "view_board";
    }

}
