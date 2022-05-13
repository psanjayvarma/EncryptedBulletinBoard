package edu.uwb.project.encryptedbulletinboard.controller;

import edu.uwb.project.encryptedbulletinboard.model.BoardModel;
import edu.uwb.project.encryptedbulletinboard.model.MessageModel;
import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import edu.uwb.project.encryptedbulletinboard.service.BoardService;
import edu.uwb.project.encryptedbulletinboard.service.EncryptionService;
import edu.uwb.project.encryptedbulletinboard.service.MessageService;
import edu.uwb.project.encryptedbulletinboard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/*
 * The Main Controller is responsible to load the templates
 * based on the GET and POST service calls.
 */
@Controller
public class MainController {

    // All the service objects
    private final UserService userService;
    private final BoardService boardService;
    private final MessageService messageService;
    private final EncryptionService encryptionService;

    //Constructor to initialize the service objects
    public MainController(UserService userService, BoardService boardService, MessageService messageService, EncryptionService encryptionService) {
        this.userService = userService;
        this.boardService = boardService;
        this.messageService = messageService;
        this.encryptionService = encryptionService;
    }

    // GET for login/index page
    @GetMapping("/")
    public String getIndexPage(HttpSession session, Model model){
        //check if session has user details
        if(session.getAttribute("user") == null || session.getAttribute("user").equals("")){
            //load login template
            return "login_page";
        }
        //load welcome template
        return "welcome_page";

    }

    // POST for login/index page
    @PostMapping("/")
    public String login(@ModelAttribute UserModel userModel, HttpSession session, Model model){
        //add user details to the session
        session.setAttribute("user", userModel);
        //validate user details
        UserModel user = userService.authenticate(userModel.getUsername(), userModel.getPassword());

        //true
        if(user != null){
            //add user to the session
            session.setAttribute("user", user);
            return "welcome_page";
        } // false
        else{
            //add error message and load login template
            model.addAttribute("loginError", "Invalid Login Credentials");
            return "login_page";
        }
    }

    // GET for register page
    @GetMapping("/register")
    public String getRegisterPage(Model model){
        //add new user model to page
        model.addAttribute("registerRequest", new UserModel());

        //load registration page template
        return "register_page";
    }

    // POST for register page
    @PostMapping("/register")
    public String register(@ModelAttribute UserModel userModel, Model model){
        //register the new user
        UserModel registeredUser = userService.registerUser(userModel.getUsername(), userModel.getPassword(), userModel.getName());

        //if user exists
        if(registeredUser == null){
            //add error message
            model.addAttribute("registrationError", "User Already Exists");
            //load register page template
            return "register_page";
        }
        //add success message
        model.addAttribute("userRegistered","New User Registered Successfully. Login with using Credentials");
        //load login template
        return "login_page";
    }

    // Function to sign out from the session
    @GetMapping("/signout")
    public String getSingOutPage(HttpSession session, Model model){
        //clear session user details
        session.setAttribute("user", null);

        //load index page
        return "redirect:/";
    }

    // GET for creating a new board
    @GetMapping("/createboard")
    public String getCreateBoardPage(HttpSession session, Model model){
        //check if session has user details
        if(session.getAttribute("user") == null || session.getAttribute("user").equals("")){
            //load index/login page
            return "redirect:/";
        }

        // add board model to page
        model.addAttribute("board", new BoardModel());
        //load create board template
        return "create_board";
    }

    // POST for creating a new board
    @PostMapping("/createboard")
    public String createNewBoard(@ModelAttribute UserModel userModel, BoardModel boardModel, HttpSession session, Model model){
        //get user details from session
        UserModel user = (UserModel) session.getAttribute("user");

        //create a new board
        BoardModel newBoard = boardService.createNewBoard(boardModel.getName());

        //add board to the user's boards list
        userService.addNewBoard(user, newBoard);

        // load home page
        return "redirect:/";
    }

    // GET for joining a board
    @GetMapping("/joinboard")
    public String getJoinBoardPage(@RequestParam(required = false) String error, HttpSession session, Model model){

        //check if session has user details
        if(session.getAttribute("user") == null || session.getAttribute("user").equals("")){
            //load index/login page
            return "redirect:/";
        }

        //add id and error message
        model.addAttribute("Id", 0);
        model.addAttribute("JoinError", error);

        //load join board template
        return "join_board";
    }

    // POST for joining a board
    @PostMapping("/joinboard")
    public String joinBoard(Integer Id,HttpSession session, Model model){
        //get user details from session
        UserModel user = (UserModel) session.getAttribute("user");

        //get board details based on id
        BoardModel board = boardService.getBoard(Id);

        //if board is not found
        if(board == null){
            //add error message to the page
            model.addAttribute("JoinError", "Board Doesn't Exist");
            return "join_board";
        }
        //if user is part of the board
        if(userService.hasTheBoard(user,Id)){
            //add error message to the page
            model.addAttribute("JoinError", "You already joined the Board");
            return "join_board";
        }

        //add board to the user's list
        userService.addNewBoard(user, board);

        //load index/login page
        return "redirect:/";
    }

    // GET for joining a board
    @GetMapping("/exitboard")
    public String getExitBoardPage(@RequestParam(required = false) String error, HttpSession session, Model model){

        //check if session has user details
        if(session.getAttribute("user") == null || session.getAttribute("user").equals("")){
            //load index/login page
            return "redirect:/";
        }

        //add id and error message to model
        model.addAttribute("Id", 0);
        model.addAttribute("ExitError", error);

        //load exit board template
        return "exit_board";
    }

    // POST for joining a board
    @PostMapping("/exitboard")
    public String exitBoard(@ModelAttribute UserModel userModel, Integer Id,HttpSession session, Model model){

        //get user details from session
        UserModel user = (UserModel) session.getAttribute("user");

        //get board details based on id
        BoardModel board = boardService.getBoard(Id);

        //if board is not found
        if(board == null){

            //add error message and load the template
            model.addAttribute("ExitError", "Board Doesn't Exist");
            return "exit_board";
        }
        //if user is not part of the board
        if(! userService.hasTheBoard(user,Id)){
            //add error message and load the template
            model.addAttribute("ExitError", "You don't belong the Board");
            return "exit_board";
        }

        //remove the board from the users list
        userService.exitBoard(user, board);

        //load index/login page
        return "redirect:/";
    }


    //Load a board
    @GetMapping("/board/{board_id}")
    public String viewBoardPage(@PathVariable("board_id") Integer Id, @RequestParam(required = false) String key, HttpSession session, Model model){

        //get board details
        BoardModel board = boardService.getBoard(Id);

        //check if session has user details
        if(session.getAttribute("user") == null || session.getAttribute("user").equals("")){
            //load index/login page
            return "redirect:/";
        } else {
            //get user details from the session
            UserModel user = (UserModel) session.getAttribute("user");

            //if user is part of the board
            if(userService.hasTheBoard(user, Id)){
                //get list of messages in the board
                List<MessageModel> messages = messageService.getMessages(Id);

                //add details to the template
                model.addAttribute("message", new MessageModel());
                model.addAttribute("messages", messages);
                model.addAttribute("board", board);
                model.addAttribute("key", new String());
                model.addAttribute("newkey", key);
                model.addAttribute("osgmsg", null);

                //load board template
                return "view_board";
            } else {
                    //redirect to join board page
                    return "redirect:/joinboard?error=Join%20Board%20ID:"+Id;
            }
        }

    }

    //Post a message
    @PostMapping("/board/{board_id}")
    public String postMessage(@PathVariable("board_id") Integer id, HttpSession session, @ModelAttribute MessageModel message){

        //check if session has user details
        if(session.getAttribute("user") == null || session.getAttribute("user").equals("")){
            //load index/login page
            return "redirect:/";
        } else {
            //get user details from the session
            UserModel user = (UserModel) session.getAttribute("user");
            //if user is part of the board
            if(userService.hasTheBoard(user, id)){
                //generate a new key
                String key = encryptionService.generateKey();

                // Encrypt the message
                String encryptMessage = encryptionService.encrypt(message.getText(), encryptionService.stringToKey(key));

                //add message details to the database
                messageService.postNewMessage(id, user, encryptMessage, key);
                //redirect back to boards page and display key
                return "redirect:/board/"+id+"?key="+key;
            } else {
                //redirect to join board page
                return "redirect:/joinboard?error=Join%20Board%20ID:"+id;
            }
        }

    }

    //Get for message decryption
    @GetMapping("/decrypt/{boardId}/{messageId}")
    public String getDecryptMessage(@PathVariable("boardId") Integer boardId){
        //Redirect to board page on get decrypt url
        return "redirect:/board/"+boardId;
    }

    //Post for message decryption
    @PostMapping("/decrypt/{boardId}/{messageId}")
    public String decryptMessage(@PathVariable("boardId") Integer boardId, @PathVariable("messageId") Integer messageId, HttpSession session, Model model, String key){
        //get board details from id
        BoardModel board = boardService.getBoard(boardId);

        //load all the messages in the board
        List<MessageModel> messages = messageService.getMessages(boardId);
        for (MessageModel message : messages){
            //for the message that needs to be decrypted
            if(message.getId() == messageId){
                //decrypt the message
                String originalMessage = encryptionService.decrypt(message.getText(), encryptionService.stringToKey(key));
                message.setText(originalMessage);
            }
        }

        // add objects to the template
        model.addAttribute("message", new MessageModel());
        model.addAttribute("messages", messages);
        model.addAttribute("board", board);
        model.addAttribute("key", new String());

        //load the board template
        return "view_board";
    }

    //GET for loading messages and keys
    @GetMapping("/message/keys")
    public String getMessageKeys(HttpSession session, Model model){
        //check if session has user details
        if(session.getAttribute("user") == null || session.getAttribute("user").equals("")){
            //load index/login page
            return "redirect:/";
        } else {
            //get user details from the session
            UserModel user = (UserModel) session.getAttribute("user");

            //add objects to the template
            model.addAttribute("messages", messageService.getMessageKeys(user.getUsername()));

            //load the message and keys template
            return "message_keys";
        }
    }

    // GET for loading all the users in the board
    @GetMapping("/board/{id}/users")
    public String viewBoardUsers(@PathVariable("id") Integer Id, HttpSession session, Model model){

        //check if session has user details
        if(session.getAttribute("user") == null || session.getAttribute("user").equals("")){
            //load index/login page
            return "redirect:/";
        } else {
            //get user details from the session
            UserModel user = (UserModel) session.getAttribute("user");

            //if user is part of the board
            if(userService.hasTheBoard(user, Id)){
                //add objects to template
                model.addAttribute("users", boardService.getUsers(Id));

                //load boards and users template
                return "board_users";
            } else {
                //redirect to join board page
                return "redirect:/joinboard?error=Join%20Board%20ID:"+Id;
            }
        }

    }
}
