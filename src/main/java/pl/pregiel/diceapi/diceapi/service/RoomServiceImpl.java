package pl.pregiel.diceapi.diceapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pregiel.diceapi.diceapi.model.Room;
import pl.pregiel.diceapi.diceapi.repository.RoomRepository;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService{
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public void save(Room room) {
        room.setPassword(encoder.encode(room.getPassword()));
        roomRepository.save(room);
    }

    @Override
    public Room findById(int id) {
        return roomRepository.findById(id);
    }

    @Override
    public List<Room> findByTitle(String title) {
        return roomRepository.findByTitle(title);
    }
}
