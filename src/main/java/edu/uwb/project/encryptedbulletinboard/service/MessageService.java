package edu.uwb.project.encryptedbulletinboard.service;

import edu.uwb.project.encryptedbulletinboard.model.MessageModel;
import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import edu.uwb.project.encryptedbulletinboard.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The MessageService class is the binding between the MessageModel and MessageRepository instances.
 * The Message Service meets the goals of application from the Services point of view.
 * The key function of this class is to post messages to a board.
 */
@Service
public class MessageService {

    // An instance of the MessageRepository interface. This instance is used to access the database.
    private final MessageRepository messageRepository;

    // A constructor that binds the MessageRepository instance.
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /** A function used to retrieve all the message posted to a board.
       The function accepts a parameter BoardID.
       The function returns a list of messages posted on that particular board. */
    public List<MessageModel> getMessages(Integer boardId){
        return messageRepository.findAllByBoardId(boardId).orElse(null);
    }

    /** A function used to post a message onto a board.
       The function accepts the following parameters-
       ID of the Board, The User attributes, The Message posted, and the Key.
       The function saves all these attribute together in the MessageRepository (Database)
     */
    public MessageModel postNewMessage(Integer boardId, UserModel user, String text, String key)
    {
        //Creation of a MessageModel instance.
        MessageModel message = new MessageModel();
        // Set the boardId of the MessageModel instance.
        message.setBoardId(boardId);
        // Set the username of the MessageModel instance.
        message.setUsername(user.getUsername());
        // Set the message of the MessageModel instance.
        message.setText(text);
        // Set the key of the MessageModel instance.
        message.setKey(key);

        // Save the MessageModel instance and it's contents to the database.
        return messageRepository.save(message);
    }

    // A function used to retrieve a list of all the keys assigned to a user's messages.
    public List<MessageModel> getMessageKeys(String username){
        return messageRepository.findAllByUsername(username).orElse(null);
    }
}