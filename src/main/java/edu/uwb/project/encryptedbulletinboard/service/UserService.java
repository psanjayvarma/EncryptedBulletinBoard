package edu.uwb.project.encryptedbulletinboard.service;

import edu.uwb.project.encryptedbulletinboard.model.BoardModel;
import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import edu.uwb.project.encryptedbulletinboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User Service is responsible to provide necessary
 * methods that deal with the User Model to the Controller
 */
@Service
public class UserService {
    //Object for User Repository
    private final UserRepository userRepository;

    //Constructor to initialize repository objects
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method to create a new user and save it to the repository.
     */
    public UserModel registerUser(String username, String password, String name){
        //if username and password are null
        if(username == null && password == null){
            return null;
        } else {
            //if username is already present in the database
            if(userRepository.findFirstByUsername(username).isPresent()){
                return null;
            }
            //Initialize the User Model
            List<BoardModel> boardModels = new ArrayList<>();
            UserModel userModel = new UserModel();
            userModel.setUsername(username);
            userModel.setPassword(password);
            userModel.setName(name);
            userModel.setBoards(boardModels);

            //save the user to the database
            return userRepository.save(userModel);
        }
    }

    /**
     * Method to check if the username and password matches
     */
    public UserModel authenticate(String username, String password){
        //return UserModel object if valid else return null
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    /**
     * Method to add a board object to the list of boards of a user
     */
    public UserModel addNewBoard(UserModel userModel, BoardModel boardModel){
        //get the current boards list
        List<BoardModel> currentList = userModel.getBoards();
        //add the new board
        currentList.add(boardModel);
        //update the list
        userModel.setBoards(currentList);
        //update the database
        return userRepository.save(userModel);
    }

    /**
     * Method to check if given board is part of the user's list
     */
    public boolean hasTheBoard(UserModel userModel, Integer Id){
        //get the current boards of the user
        List<BoardModel> boards  = userModel.getBoards();
        for(int i =0; i < boards.size(); i++){
            //if board id is in the list
            if(boards.get(i).getId() == Id){
                //true
                return true;
            }
        }
        //false
        return false;
    }

    /**
     * Method to remove a board from the list of boards of a user
     */
    public UserModel exitBoard(UserModel userModel, BoardModel boardModel) {

        //get the current boards of the user
        List<BoardModel> currentList = userModel.getBoards();
        for(int i =0; i < currentList.size(); i++){
            //if board id is in the list
            if(currentList.get(i).getId() == boardModel.getId()){
                //remove the list item
                currentList.remove(i);
            }
        }
        //update the users list of boards
        userModel.setBoards(currentList);
        //update the database
        return userRepository.save(userModel);
    }
}
