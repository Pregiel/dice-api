package pl.pregiel.diceapi.diceapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pregiel.diceapi.diceapi.model.Room;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
    Room findById(int id);
    List<Room> findByTitle(String title);
}
