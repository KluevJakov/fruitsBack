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
import ru.jafix.fruties.entities.dto.UserDto;
import ru.jafix.fruties.repositories.UserRepository;
import ru.jafix.fruties.services.UserService;


import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UserController {

    @Autowired
    protected UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User u;
        try {
            u = userService.save(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Такой логин уже занят");
        }
        return ResponseEntity.ok(u);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody UserDto user) {
        try {
            return ResponseEntity.ok(userService.auth(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
