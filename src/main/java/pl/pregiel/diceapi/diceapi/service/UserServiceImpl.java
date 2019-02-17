package pl.pregiel.diceapi.diceapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pregiel.diceapi.diceapi.model.Role;
import pl.pregiel.diceapi.diceapi.model.User;
import pl.pregiel.diceapi.diceapi.repository.RoleRepository;
import pl.pregiel.diceapi.diceapi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Override
    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRole(roleRepository.findById(1));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User login(String login, String password) {
        User user = findByUsername(login);
        if (user == null) {
            return null;
        }

        if (encoder.matches(user.getPassword(), password)) {
            return null;
        }

        return user;
    }
}
