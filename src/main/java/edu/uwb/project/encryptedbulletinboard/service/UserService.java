package edu.uwb.project.encryptedbulletinboard.service;

import edu.uwb.project.encryptedbulletinboard.model.BoardModel;
import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import edu.uwb.project.encryptedbulletinboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel registerUser(String username, String password, String name){
        
        if(username == null && password == null){
            return null;
        } else {
            if(userRepository.findFirstByUsername(username).isPresent()){
                System.out.println("User already present");
                return null;
            }
            List<BoardModel> boardModels = new ArrayList<>();
            UserModel userModel = new UserModel();
            userModel.setUsername(username);
            userModel.setPassword(password);
            userModel.setName(name);
            userModel.setBoards(boardModels);
            return userRepository.save(userModel);
        }
    }

    public UserModel authenticate(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    public UserModel addNewBoard(UserModel userModel, BoardModel boardModel){
        List<BoardModel> currentList = userModel.getBoards();
        currentList.add(boardModel);
        userModel.setBoards(currentList);
        return userRepository.save(userModel);
    }

    public boolean hasTheBoard(UserModel userModel, Integer Id){
        List<BoardModel> boards  = userModel.getBoards();
        for(int i =0; i < boards.size(); i++){
            if(boards.get(i).getId() == Id){
                return true;
            }
        }
        return false;
    }


    public UserModel exitBoard(UserModel userModel, BoardModel boardModel) {
        List<BoardModel> currentList = userModel.getBoards();
        for(int i =0; i < currentList.size(); i++){
            if(currentList.get(i).getId() == boardModel.getId()){
                currentList.remove(i);
            }
        }
        userModel.setBoards(currentList);
        return userRepository.save(userModel);
    }
}
