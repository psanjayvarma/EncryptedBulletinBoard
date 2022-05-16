package edu.uwb.project.encryptedbulletinboard.service;

import edu.uwb.project.encryptedbulletinboard.model.BoardModel;
import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import edu.uwb.project.encryptedbulletinboard.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
        "spring.datasource.url = jdbc:postgresql://localhost:5432/bulletin_test",
        "spring.datasource.driver-class-name=org.postgresql.Driver",
        "spring.datasource.username=db_user",
        "spring.datasource.password=password"
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest{

    @Autowired
    private UserRepository userRepository;


    List<BoardModel> boardModels = new ArrayList<>();

    private final List<BoardModel> boards = new ArrayList<>();

    UserModel userModel = new UserModel();

    @Test
    @Order(1)
    void registerUser() {

        userModel.setUsername("123");
        userModel.setPassword("admin");
        userModel.setName("tester@testmail.com");

        userRepository.save(userModel);
        userModel = userRepository.findFirstByUsername("123").orElse(null);

        assertEquals("123", userModel.getUsername());
        assertEquals("admin", userModel.getPassword());
        assertEquals("tester@testmail.com", userModel.getName());
    }

    @Test
    @Order(2)
    void authenticate() {
        assertNotNull(userRepository.findByUsernameAndPassword("dodo", "admin"));
    }

    @Test
    @Order(3)
    void addNewBoard() {
        userModel = userRepository.findByUsernameAndPassword("123", "admin").orElse(null);
        BoardModel boardModel = new BoardModel();
        boardModel.setName("testBoard");
        boardModels.add(boardModel);
        //System.out.println("See this  ------" + boardModels);
        List<BoardModel> currentList = boardModels;
        userModel.setBoards(currentList);
        userRepository.save(userModel);
        assertEquals("testBoard", userModel.getBoards().get(0).getName());
    }

    @Test
    @Transactional
    @Order(4)
    void hasTheBoard() {
        userModel = userRepository.findByUsernameAndPassword("123", "admin").orElse(null);
        System.out.println(userModel);
        assertEquals("testBoard", userModel.getBoards().get(0).getName());
    }

    @Test
    @Transactional
    @Order(5)
    void exitBoard() {
        List<BoardModel> boards = new ArrayList<>();
        userModel = userRepository.findByUsernameAndPassword("123", "admin").orElse(null);
        boards = userModel.getBoards();
        //System.out.println("Size     " + boards.size());
        for(int i = 0; i < boards.size(); i++)
        {
            //System.out.println("My name is    " + boards.get(i).getName());
            if(boards.get(i).getName().equals("testBoard"))
            {
                System.out.println("I have been executed ---------------" + boards.get(i).getName());
                boards.remove(i);
            }
        }
        //Simplifying for now
        //boards.remove(0);
        userModel.setBoards(boards);
        userRepository.save(userModel);
        //assertNull(boards.get(0));
    }

    @Test
    @Order(6)
    void registerUserThatFails()
    {
        userModel.setUsername("123456789");
        userModel.setPassword("adminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadminadmin");
        userModel.setName("tester@testmail.com");

        try{
            userRepository.save(userModel);
        }
        catch(Exception e)
        {
            System.out.println("Password is too long");
            fail();
        }
    }

    @Test
    @Order(7)
    void authenticateThatFails() {
        //This user does not exist
        userModel = userRepository.findByUsernameAndPassword("1234567", "admin").orElse(null);
        assertNull(userModel);
    }

}