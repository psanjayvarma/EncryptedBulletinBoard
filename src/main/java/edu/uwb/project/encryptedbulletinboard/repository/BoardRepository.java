package edu.uwb.project.encryptedbulletinboard.repository;

import edu.uwb.project.encryptedbulletinboard.model.BoardModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardModel, Integer> {
}
