package edu.uwb.project.encryptedbulletinboard.repository;

import edu.uwb.project.encryptedbulletinboard.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageModel, Integer> {

    Optional<List<MessageModel>> findAllByBoardId(Integer boardId);

    Optional<List<MessageModel>> findAllByLogin(String login);

}
