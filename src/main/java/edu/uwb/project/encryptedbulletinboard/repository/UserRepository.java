package edu.uwb.project.encryptedbulletinboard.repository;

import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface is used to communicate with the database.
 * This interface works along with the BoardModel instance to store the information related to a Board in the database.
 */
@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    // A function used to find the first user in the database with a particular Username and Password combination.
    Optional<UserModel> findByUsernameAndPassword(String username, String password);

    // A function used to find the first Board in the database with a username same as that passed to the function.
    Optional<UserModel> findFirstByUsername(String username);

}