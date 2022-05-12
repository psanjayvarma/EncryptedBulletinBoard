package edu.uwb.project.encryptedbulletinboard.service;

import edu.uwb.project.encryptedbulletinboard.model.MessageModel;
import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import edu.uwb.project.encryptedbulletinboard.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageModel> getMessages(Integer boardId){
        return messageRepository.findAllByBoardId(boardId).orElse(null);
    }

    public MessageModel postNewMessage(Integer boardId, UserModel user, String text, String key){
        MessageModel message = new MessageModel();

        message.setBoardId(boardId);
        message.setLogin(user.getLogin());
        message.setText(text);
        message.setKey(key);
        return messageRepository.save(message);
    }

    public List<MessageModel> getMessageKeys(String login){
        return messageRepository.findAllByLogin(login).orElse(null);
    }


}
