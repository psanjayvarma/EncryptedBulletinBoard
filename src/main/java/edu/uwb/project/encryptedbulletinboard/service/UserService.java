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

    public UserModel registerUser(String login, String password, String email){
        
        if(login == null && password == null){
            return null;
        } else {
            if(userRepository.findFirstByLogin(login).isPresent()){
                System.out.println("User already present");
                return null;
            }
            List<BoardModel> boardModels = new ArrayList<>();
            UserModel userModel = new UserModel();
            userModel.setLogin(login);
            userModel.setPassword(password);
            userModel.setEmail(email);
            userModel.setBoards(boardModels);
            return userRepository.save(userModel);
        }
    }

    public UserModel authenticate(String login, String password){
        return userRepository.findByLoginAndPassword(login, password).orElse(null);
    }

    public UserModel addNewBoard(UserModel userModel, BoardModel boardModel){
        userModel.addBoard(boardModel);
        return userRepository.save(userModel);
    }

}
