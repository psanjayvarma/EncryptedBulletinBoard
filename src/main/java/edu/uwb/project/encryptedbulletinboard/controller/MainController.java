package edu.uwb.project.encryptedbulletinboard.controller;

import edu.uwb.project.encryptedbulletinboard.model.BoardModel;
import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import edu.uwb.project.encryptedbulletinboard.service.BoardService;
import edu.uwb.project.encryptedbulletinboard.service.UserService;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    private final UserService userService;
    private final BoardService boardService;

    public MainController(UserService userService, BoardService boardService) {
        this.userService = userService;
        this.boardService = boardService;
    }

    // GET and POST for login/index page
    @GetMapping("/")
    public String getIndexPage(HttpSession session, Model model){
        if(session.getAttribute("user") == null || session.getAttribute("user").equals("")){
            session.setAttribute("user", new UserModel());
            return "login_page";
        }
        System.out.println("Session found ...." + session.getAttribute("user"));
        return "welcome_page";

    }

    @PostMapping("/")
    public String login(@ModelAttribute UserModel userModel, HttpSession session, Model model){
        session.setAttribute("user", userModel);
        UserModel user = userService.authenticate(userModel.getLogin(), userModel.getPassword());
        if(user != null){
            session.setAttribute("user", user);
            return "welcome_page";
        }
        else{
            model.addAttribute("loginError", "Invalid Login Credentials");
            return "login_page";
        }
    }

    // GET and POST for register page
    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new UserModel());
        return "register_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserModel userModel, Model model){
        UserModel registeredUser = userService.registerUser(userModel.getLogin(), userModel.getPassword(), userModel.getEmail());
        System.out.println("Register request for ...." + registeredUser);
        if(registeredUser == null){
            model.addAttribute("registrationError", "User Already Exists");
            return "register_page";
        }
        model.addAttribute("userRegistered","New User Registered Successfully. Login with using Credentials");
        return "login_page";
    }

    // Function to sign out from the session
    @GetMapping("/signout")
    public String getSingOutPage(HttpSession session, Model model){
        session.setAttribute("user", null);
        return "redirect:/";
    }

    // GET and POST for creating a new board
    @GetMapping("/createboard")
    public String getCreateBoardPage(HttpSession session, Model model){
        if(session.getAttribute("user") == null || session.getAttribute("user").equals("")){
            return "redirect:/";
        }
        model.addAttribute("board", new BoardModel());
        return "create_board";
    }

    @PostMapping("/createboard")
    public String createNewBoard(@ModelAttribute UserModel userModel, BoardModel boardModel, HttpSession session, Model model){
        UserModel user = (UserModel) session.getAttribute("user");
        BoardModel newBoard = boardService.createNewBoard(boardModel.getName());
        System.out.println("New Board Creation request for ...." + newBoard +"by user "+ user);
        user = userService.addNewBoard(user, newBoard);
        model.addAttribute("user", user);
        return "redirect:/";
    }

    // GET and POST for joining a board
    @GetMapping("/joinboard")
    public String getJoinBoardPage(@RequestParam(required = false) String error, HttpSession session, Model model){
        if(session.getAttribute("user") == null || session.getAttribute("user").equals("")){
            return "redirect:/";
        }
        model.addAttribute("Id", 0);
        model.addAttribute("JoinError", error);
        return "join_board";
    }

    @PostMapping("/joinboard")
    public String joinBoard(@ModelAttribute UserModel userModel, Integer Id,HttpSession session, Model model){
        UserModel user = (UserModel) session.getAttribute("user");
        BoardModel board = boardService.getBoard(Id);
        System.out.println("Join Request for ......" + board + " by " + user);
        if(board == null){
            System.out.println("Board ID is not valid");
            model.addAttribute("JoinError", "Board Doesn't Exist");
            return "join_board";
        }
        if(userService.hasTheBoard(user,Id)){
            System.out.println("User is already in the board");
            model.addAttribute("JoinError", "You already joined the Board");
            return "join_board";
        }
        user = userService.addNewBoard(user, board);
        model.addAttribute("user", user);
        return "redirect:/";
    }

    // GET and POST for joining a board
    @GetMapping("/exitboard")
    public String getExitBoardPage(@RequestParam(required = false) String error, HttpSession session, Model model){
        if(session.getAttribute("user") == null || session.getAttribute("user").equals("")){
            return "redirect:/";
        }
        model.addAttribute("Id", 0);
        model.addAttribute("ExitError", error);
        return "exit_board";
    }

    @PostMapping("/exitboard")
    public String exitBoard(@ModelAttribute UserModel userModel, Integer Id,HttpSession session, Model model){
        UserModel user = (UserModel) session.getAttribute("user");
        BoardModel board = boardService.getBoard(Id);
        System.out.println("Exit Request for ......" + board + " by " + user);
        if(board == null){
            System.out.println("Board ID is not valid");
            model.addAttribute("ExitError", "Board Doesn't Exist");
            return "exit_board";
        }
        if(! userService.hasTheBoard(user,Id)){
            System.out.println("User is not in the board");
            model.addAttribute("ExitError", "You don't belong the Board");
            return "exit_board";
        }
        user = userService.exitBoard(user, board);
        model.addAttribute("user", user);
        return "redirect:/";
    }


    //Load a board
    @GetMapping("/board/{board_id}")
    public String viewBoardPage(@PathVariable("board_id") Integer Id, HttpSession session, Model model){
        BoardModel boardModel = boardService.getBoard(Id);
        if(session.getAttribute("user") == null || session.getAttribute("user").equals("")){
            return "redirect:/";
        } else {
            UserModel user = (UserModel) session.getAttribute("user");
            if(userService.hasTheBoard(user, Id)){
                model.addAttribute("board", boardModel);
                return "view_board";
            } else {
                    return "redirect:/joinboard?error=Join%20Board%20ID:"+Id;
            }
        }

    }
}
