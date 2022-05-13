package edu.uwb.project.encryptedbulletinboard.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * This class serves as a structure for handling the 'Board' component of the application.
 * Every board created has a Name and an ID associated to it.
 * Each BoardModel instance also contains a Many-to-Many relation mapped with UserModel instances.
 * A board can have multiple users and a user can be a part of multiple boards.
 */
@Entity
@Table(name = "boards")
public class BoardModel {
    // A unique board identifier.
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    // Name of the board.
    String name;

    // BoardModel to UserModel Mapping.
    @ManyToMany(mappedBy = "boards")
    List<UserModel> users;

    //////////// Getter and Setter functions ////////////
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }
    //////////// End Getter and Setter functions ////////////

    // A function used to check if the ID, Names and Users of a BoardModel instance are same as those of another BoardModel instance.
    @Override
    public boolean equals(Object o)
    {
        //Checking if the current object is same as the object sent as the parameter.
        if (this == o) return true;
        // If the object sent to function is null, return a false boolean value.
        if (o == null || getClass() != o.getClass()) return false;
        // Transforming the object into type BoardModel.
        BoardModel that = (BoardModel) o;

        // Returns false if either of the attributes are not same.
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(users, that.users);
    }

    // A function that returns a string containing a Board's Unique Identifier and Name.
    @Override
    public String toString() {
        return "BoardModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
