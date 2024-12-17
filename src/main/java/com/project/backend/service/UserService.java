package com.project.backend.service;

import com.project.backend.dto.UserDTO;
import com.project.backend.dto.UserLoginDTO;
import com.project.backend.entity.User;
import com.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<?> login(UserDTO userDTO) {
        Optional<User> currentUser = userRepository.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
        if (currentUser.isPresent()) {
            User user = currentUser.get();
            UserLoginDTO userLoginDTO = new UserLoginDTO();
            userLoginDTO.setUsername(user.getUsername());
            userLoginDTO.setFirstName(user.getFirstName());
            userLoginDTO.setLastName(user.getLastName());
            userLoginDTO.setEmail(user.getEmail());
            userLoginDTO.setPassword(user.getPassword());
            userLoginDTO.setId(user.getId());
            return ResponseEntity.status(HttpStatus.OK).body(userLoginDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
        }
    }

    public ResponseEntity<?> register(User user) {
        Optional<User> existingUserName = userRepository.findByUsername(user.getUsername());
        Optional<User> existingUserMail = userRepository.findByEmail(user.getEmail());

        if(existingUserMail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errorCode","EMAIL_DUPLICATE","message","This email is already in used"));
        }

        if(existingUserName.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errorCode","USERNAME_DUPLICATE","message","This username is already in used"));
        }

        User created_user = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created_user);
    }


}
