package edu.uwb.project.encryptedbulletinboard.service;

import edu.uwb.project.encryptedbulletinboard.model.BoardModel;
import edu.uwb.project.encryptedbulletinboard.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void createNewBoard() {
        BoardModel boardModel = new BoardModel();
        boardModel.setName("TestingBoardService");
        boardRepository.save(boardModel);


        //Fetching the board back from the database to verify
        BoardModel verify = new BoardModel();
        verify = boardRepository.findFirstByName("TestingBoardService").orElse(null);
        assertNotNull(verify);
    }

    //Test will fail because the name of the table is too long.
    @Test
    void createNewBoardThatExists() {
        BoardModel boardModel = new BoardModel();
        boardModel.setName("TestingBoardService1");
        boardRepository.save(boardModel);


        //Fetching the board back from the database to verify
        BoardModel verify = new BoardModel();
        verify = boardRepository.findFirstByName("TestingBoardService2").orElse(null);
        assertNull(verify);
    }

    // This test will fail since board names don't match.
    @Test
    void getBoardThatFails() {
        BoardModel verify = new BoardModel();
        verify = boardRepository.findFirstById(0).orElse(null);
        assertNull(verify);
    }
}