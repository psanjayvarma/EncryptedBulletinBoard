package edu.uwb.project.encryptedbulletinboard.service;

import edu.uwb.project.encryptedbulletinboard.model.BoardModel;
import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import edu.uwb.project.encryptedbulletinboard.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardModel createNewBoard(String name){
        BoardModel boardModel = new BoardModel();
        boardModel.setName(name);
        return boardRepository.save(boardModel);
    }

    public BoardModel getBoard(Integer Id){
        return boardRepository.findFirstById(Id).orElse(null);
    }

    public List<UserModel> getUsers(Integer id){
        return getBoard(id).getUsers();

    }

}
