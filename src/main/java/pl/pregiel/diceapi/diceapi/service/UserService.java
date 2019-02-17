package pl.pregiel.diceapi.diceapi.service;

import pl.pregiel.diceapi.diceapi.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);

    User login(String login, String password);
}
