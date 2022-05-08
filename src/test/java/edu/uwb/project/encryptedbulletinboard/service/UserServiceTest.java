package edu.uwb.project.encryptedbulletinboard.service;

import edu.uwb.project.encryptedbulletinboard.model.BoardModel;
import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import edu.uwb.project.encryptedbulletinboard.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url = jdbc:postgresql://localhost:5432/postgres",
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "spring.datasource.username=db_admin",
        "spring.datasource.password=admin"
})
class UserServiceTest{

    @Autowired
    private UserRepository userRepository;


    //private UserService userService = new UserService(userRepository);

    List<BoardModel> boardModels = new ArrayList<>();
    List<BoardModel> boards = new ArrayList<>();
    UserModel userModel = new UserModel();

    @Test
    void registerUser() {

        userModel.setLogin("123");
        userModel.setPassword("admin");
        userModel.setEmail("tester@testmail.com");

        /*BoardModel boardModel = new BoardModel();
        boardModel.setName("testBoard");
        boardModels.add(boardModel);*/

        //userModel.setBoards(boardModels);
        userRepository.save(userModel);
        userModel = userRepository.findFirstByLogin("123").orElse(null);

        assertEquals("123", userModel.getLogin());
        assertEquals("admin", userModel.getPassword());
        assertEquals("tester@testmail.com", userModel.getEmail());

    }

    @Test
    void authenticate() {
        userModel = userRepository.findByLoginAndPassword("123", "admin").orElse(null);
        if(userModel == null)
        {
            fail("NO USER");
        }
        else
        {
        }
        //assertNotNull(userRepository.findByLoginAndPassword("dodo", "admin"));
    }

    @Test
    void addNewBoard() {
        userModel = userRepository.findByLoginAndPassword("123", "admin").orElse(null);
        BoardModel boardModel = new BoardModel();
        boardModel.setName("testBoard");
        boardModels.add(boardModel);
        System.out.println("See this  ------" + boardModel);
        List<BoardModel> currentList = boardModels;
        userModel.setBoards(currentList);
        userRepository.save(userModel);
    }

    @Test
    void hasTheBoard() {
        userModel = userRepository.findByLoginAndPassword("123", "admin").orElse(null);
        boards = userModel.getBoards();
        //System.out.println("Board ID is" + boards.get(0).getId());
        assertEquals("testBoard", boards.get(0).getName());
    }

    @Test
    void exitBoard() {
        userModel = userRepository.findByLoginAndPassword("123", "admin").orElse(null);
        boards = userModel.getBoards();
        //System.out.println("Size     " + boards.size());
        for(int i = 0; i < boards.size(); i++)
        {
            //System.out.println("My name is    " + boards.get(i).getName());
            if(boards.get(i).getName().equals("testBoard"))
            {
                System.out.println("I have been executed" + boards.get(i).getName());
                boards.remove(i);
            }
        }
        //Simplifying for now
        //boards.remove(0);
        userModel.setBoards(boards);
        userRepository.save(userModel);
        //assertNull(boards.get(0));
    }
}