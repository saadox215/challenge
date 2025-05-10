package org.example.challenge.services;

import org.example.challenge.entities.User;
import org.example.challenge.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User authenticate(String email, String motDePasse) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getMotDePasse().equals(motDePasse)) {
            return user;
        }
        return null;
    }
}
