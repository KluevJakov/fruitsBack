package ru.jafix.fruties.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.jafix.fruties.entities.Role;
import ru.jafix.fruties.entities.User;
import ru.jafix.fruties.repositories.UserRepository;


import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        Optional<User> checkUser = userRepository.findByLogin(user.getLogin());

        if (checkUser.isPresent()) {
            return ResponseEntity.badRequest().body("Такой логин уже занят");
        }

        user.setRole(new Role(UUID.fromString("efe08854-9f59-4d9b-9723-1709488c4413")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
