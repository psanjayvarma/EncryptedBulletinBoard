package edu.uwb.project.encryptedbulletinboard.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * This class serves as a structure for handling the 'User' component of the application.
 * Every user of this application has the following features:
 * - A Unique ID: A user is assigned a unique ID on creation of an account.
 * - Username: Users set usernames in the application on creation of an account.
 * - Password: Users set a password on creation of an account.
 * - A Name: The name of a user is used to store the full name of a user. For example 'John Doe'.
 */
@Entity
@Table(name = "users")
public class UserModel {

    // Unque Identifier of a User.
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    // Username set by the user.
    String username;

    // A password set by the user.
    String password;

    // A user's full name.
    String name;

    /** The following relation specifies the relation between a User and Boards they are part of.
    *  Columns titled user_id (belonging to table 'users') and board_id (belonging to table 'users')
    *  are joined together to form a new table. */
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "users_boards",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "board_id")
    )
    List<BoardModel> boards;

    //////////// Getter and Setter functions ////////////
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BoardModel> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardModel> boards) {
        this.boards = boards;
    }
    //////////// End Getter and Setter functions ////////////

    /** A function used to check if the User ID, Username, Password of the user,
    *  and the full name UserModel instance are same as those of another
    *  MessageModel instance. */
    @Override
    public boolean equals(Object o)
    {
        //Checking if the current object is same as the object sent as the parameter.
        if (this == o) return true;
        // If the object sent to function is null, return a false boolean value.
        if (o == null || getClass() != o.getClass()) return false;
        // Transforming the object into type UserModel.
        UserModel userModel = (UserModel) o;

        // Returns false if either of the attributes are not same.
        return Objects.equals(id, userModel.id) && Objects.equals(username, userModel.username) && Objects.equals(password, userModel.password) && Objects.equals(name, userModel.name);
    }

    /** A function that returns a string containing a User's Unique Identifier, the
    Username and the Fullname. */
    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", login='" + username + '\'' +
                ", email='" + name + '\'' +
                '}';
    }
}
