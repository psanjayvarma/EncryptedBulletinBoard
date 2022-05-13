package edu.uwb.project.encryptedbulletinboard.repository;

import edu.uwb.project.encryptedbulletinboard.model.BoardModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This interface is used to communicate with the database.
 * This interface works along with the BoardModel instance to store the information related to a Board in the database.
 */
public interface BoardRepository extends JpaRepository<BoardModel, Integer> {

    // A function used to find the first Board in the database with a Unique ID 'Id'.
    Optional<BoardModel> findFirstById(Integer Id);

    // A function used to find the first Board in the database with the same Name as what is passed to the function.
    Optional<BoardModel> findFirstByName(String name);
}
