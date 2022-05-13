package edu.uwb.project.encryptedbulletinboard.service;

import edu.uwb.project.encryptedbulletinboard.model.BoardModel;
import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import edu.uwb.project.encryptedbulletinboard.repository.BoardRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Board Service is responsible to provide necessary
 * methods that deal with the Board Model to the Controller
 */
@Service
public class BoardService {

    //Object for Board Repository
    private final BoardRepository boardRepository;

    //Constructor to initialize repository objects
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * Method to create a new board and save it to the repository.
     */
    public BoardModel createNewBoard(String name){
        //Create new board object.
        BoardModel boardModel = new BoardModel();
        boardModel.setName(name);

        //save it to the database
        return boardRepository.save(boardModel);
    }

    /**
     * Method to get the details of the board based on the ID.
     */
    public BoardModel getBoard(Integer Id){

        //return BoardModel object for the given ID
        return boardRepository.findFirstById(Id).orElse(null);
    }

    /**
     * Method to fetch all the UserModels in the given Board ID
     */
    public List<UserModel> getUsers(Integer id){
        //return all the board users for the given ID
        return getBoard(id).getUsers();

    }

}
