package com.project.backend.service;

import com.project.backend.dto.UserDTO;
import com.project.backend.entity.User;
import com.project.backend.repository.UserRepository;
import com.project.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> login(UserDTO userDTO) {
        Optional<User> currentUser = userRepository.findByUsername(userDTO.getUsername());
        if (currentUser.isPresent()) {
            User user = currentUser.get();
            if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                String token = JwtUtil.generateToken(user.getUsername(), user.getId());
                return ResponseEntity.status(HttpStatus.OK)
                        .body(Map.of("token", token, "username", user.getUsername()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username");
        }
    }


    public ResponseEntity<?> register(User user) {
        Optional<User> existingUserName = userRepository.findByUsername(user.getUsername());
        Optional<User> existingUserMail = userRepository.findByEmail(user.getEmail());

        if (existingUserMail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("errorCode", "EMAIL_DUPLICATE", "message", "This email is already in use"));
        }

        if (existingUserName.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("errorCode", "USERNAME_DUPLICATE", "message", "This username is already in use"));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userRepository.save(user);

        UserDTO responseDTO = new UserDTO();
        responseDTO.setId(createdUser.getId());
        responseDTO.setUsername(createdUser.getUsername());
        responseDTO.setFirstName(createdUser.getFirstName());
        responseDTO.setLastName(createdUser.getLastName());
        responseDTO.setEmail(createdUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }




}
