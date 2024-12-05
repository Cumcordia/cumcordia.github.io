package kz.goldenfish.goldenfish.service;
import kz.goldenfish.goldenfish.model.User;
import kz.goldenfish.goldenfish.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static kz.goldenfish.goldenfish.model.User.Role.ADMIN;
import static kz.goldenfish.goldenfish.model.User.Role.USER;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        user.setUsername(user.getUsername());
        user.setRole(USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setPhone(user.getPhone());
        user.setStatus(User.Status.NON_VERIFIED);
        return userRepository.save(user);
    }

    public User saveAdmin(User user) {
        user.setUsername(user.getUsername());
        user.setRole(ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setPhone(user.getPhone());
        user.setStatus(User.Status.VERIFIED);
        return userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
