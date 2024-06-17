package ru.jafix.fruties.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.jafix.fruties.entities.Role;
import ru.jafix.fruties.entities.User;
import ru.jafix.fruties.entities.dto.JwtDto;
import ru.jafix.fruties.entities.dto.UserDto;
import ru.jafix.fruties.repositories.RoleRepository;
import ru.jafix.fruties.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected JwtService jwtService;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    public User getById(UUID id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new RuntimeException(String.format("User with id = %s, not found", id));
        }

        return user.get();
    }

    public User save(User user) {
        Optional<User> userFromDb = userRepository.findByLogin(user.getLogin());

        if (userFromDb.isPresent()) {
            throw new RuntimeException("Пользователь с таким логином уже зарегистрирован");
        }

        Optional<Role> userRole = roleRepository.findById(UUID.fromString("efe08854-9f59-4d9b-9723-1709488c4413"));
        user.setRole(userRole.get());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public JwtDto auth(UserDto user) {
        Optional<User> userFromDb = userRepository.findByLogin(user.getLogin());

        if (userFromDb.isEmpty()) {
            throw new RuntimeException("Пользователь с таким логином не найден");
        }

        if (!passwordEncoder.matches(user.getPassword(), userFromDb.get().getPassword())) {
            throw new RuntimeException("Неверный пароль");
        }

        return new JwtDto(jwtService.generate(user.getLogin()));
    }

}
