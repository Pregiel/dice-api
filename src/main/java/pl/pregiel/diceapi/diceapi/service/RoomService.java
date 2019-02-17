package pl.pregiel.diceapi.diceapi.service;

import pl.pregiel.diceapi.diceapi.model.Room;

import java.util.List;

public interface RoomService {
    void save(Room room);

    Room findById(int id);

    List<Room> findByTitle(String title);
}
