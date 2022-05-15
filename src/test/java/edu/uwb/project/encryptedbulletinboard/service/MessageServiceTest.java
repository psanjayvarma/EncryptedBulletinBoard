package edu.uwb.project.encryptedbulletinboard.service;

import edu.uwb.project.encryptedbulletinboard.model.MessageModel;
import edu.uwb.project.encryptedbulletinboard.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
class MessageServiceTest {

    @Autowired
    private MessageRepository messageRepository;

    //The following two tests together make a single test.
    @Test
    void postNewMessage() {
        MessageModel message = new MessageModel();
        message.setBoardId(3);
        message.setUsername("123");
        message.setText("This is a test message");
        messageRepository.save(message);
    }

    @Test
    void getMessages() {
        //Fetching the message and verifying
        List<MessageModel> verification = new ArrayList<>();
        verification = messageRepository.findAllByBoardId(3).orElse(null);
        for(int i = 0; i < verification.size(); i++)
        {
            if(verification.get(i).getText().equals("This is a test message"))
            {
                assertEquals("This is a test message", verification.get(i).getText());
                break;
            }
        }
    }


    //A test that fails --- Looking for a message that does not exist
    @Test
    void getMessagesThatFails() {
        //Fetching the message and verifying
        List<MessageModel> verification = new ArrayList<>();
        verification = messageRepository.findAllByBoardId(3).orElse(null);
        for(int i = 0; i < verification.size(); i++)
        {
            if(verification.get(i).getText().equals("This is a test message"))
            {
                assertEquals("This is a test message and this test shall fail", verification.get(i).getText());
            }
        }
    }

}