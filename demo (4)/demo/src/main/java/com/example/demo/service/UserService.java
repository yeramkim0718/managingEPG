package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        user.setEnabled(true);

        Role role = new Role ();
        role.setId(1L);
        user.getRoles().add(role);

        return userRepository.save(user);
    }


    public Long findIdbyUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user.getId();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
