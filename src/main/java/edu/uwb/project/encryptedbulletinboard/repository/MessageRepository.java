package edu.uwb.project.encryptedbulletinboard.repository;

import edu.uwb.project.encryptedbulletinboard.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * This interface is used to communicate with the database.
 * This interface works along with the MessageModel instance to store the information related to a Message in the database.
 */
public interface MessageRepository extends JpaRepository<MessageModel, Integer> {

    // A function used to find all the message posted on a Board with an Id of the board passed as the parameter.
    Optional<List<MessageModel>> findAllByBoardId(Integer boardId);

    // A function used to find all the message posted by a user with a particular username.
    Optional<List<MessageModel>> findAllByUsername(String username);

}
