package pl.pregiel.diceapi.diceapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import pl.pregiel.diceapi.diceapi.View;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({View.RoomListView.class, View.RoomView.class})
    private Integer id;

    @Column(nullable = false)
    @JsonView({View.RoomListView.class, View.RoomView.class})
    private String title;

    @Column(nullable = false)
    private String password;

    @ManyToMany(mappedBy = "roomList")
    @JsonView({View.RoomView.class})
    private final List<User> userList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getUserList() {
        return userList;
    }
}
