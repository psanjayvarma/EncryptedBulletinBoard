package edu.uwb.project.encryptedbulletinboard.repository;

import edu.uwb.project.encryptedbulletinboard.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findByUsernameAndPassword(String username, String password);

    Optional<UserModel> findFirstByUsername(String username);

}