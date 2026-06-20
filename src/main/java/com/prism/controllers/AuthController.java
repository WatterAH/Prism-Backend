package com.prism.controllers;

import com.prism.models.User;
import com.prism.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Optional<User> userOpt = userRepository.findByUsernameAndPassword(username, password);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", Map.of(
                            "userId", user.getUserId(),
                            "username", user.getUsername(),
                            "role", user.getRole() != null ? user.getRole() : ""
                    ),
                    "message", "Autenticación válida"
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "success", false,
                "data", Map.of(),
                "message", "Usuario no registrado en la aplicación web"
        ));
    }
}
