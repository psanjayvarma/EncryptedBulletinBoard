package edu.uwb.project.encryptedbulletinboard.repository;

import edu.uwb.project.encryptedbulletinboard.model.BoardModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardModel, Integer> {

    Optional<BoardModel> findFirstById(Integer Id);

    Optional<BoardModel> findFirstByName(String name);


}
