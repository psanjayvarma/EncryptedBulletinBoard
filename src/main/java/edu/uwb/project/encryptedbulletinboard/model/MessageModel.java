package edu.uwb.project.encryptedbulletinboard.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "messages")
public class MessageModel {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String text;

    Integer boardId;

    String username;

    String key;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageModel that = (MessageModel) o;
        return Objects.equals(id, that.id) && Objects.equals(text, that.text) && Objects.equals(boardId, that.boardId) && Objects.equals(username, that.username) && Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, boardId, username, key);
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", boardId=" + boardId +
                ", login='" + username + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
