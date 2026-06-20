package com.prism.controllers;

import com.prism.models.User;
import com.prism.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para la autenticación de usuarios.
 *
 * @RestController: combina @Controller y @ResponseBody, convierte automáticamente
 *   los valores de retorno a JSON.
 * @RequestMapping: define el prefijo de ruta para todos los endpoints de esta clase.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Spring inyecta automáticamente la implementación del repositorio
    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint de inicio de sesión.
     * Recibe: { "username": "...", "password": "..." }
     * Devuelve siempre la misma estructura: { success, data, message }
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Busca el usuario por username y password en la base de datos
        Optional<User> userOpt = userRepository.findByUsernameAndPassword(username, password);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Autenticación exitosa: devuelve 200 con los datos del usuario
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

        // Usuario no encontrado: devuelve 401 Unauthorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "success", false,
                "data", Map.of(),
                "message", "Usuario no registrado en la aplicación web"
        ));
    }
}
