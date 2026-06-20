package com.prism.models;

import jakarta.persistence.*;

/**
 * Entidad que representa a un usuario administrador del sistema.
 * Se mapea a la tabla "users" en la base de datos MySQL.
 */
@Entity
@Table(name = "users")
public class User {

    // Llave primaria autoincremental gestionada por MySQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    // El nombre de usuario debe ser único y no puede ser nulo
    @Column(nullable = false, unique = true)
    private String username;

    // Contraseña en texto plano (sin cifrado, por simplicidad del proyecto)
    @Column(nullable = false)
    private String password;

    // Rol del usuario (ej. "ADMIN"). Puede ser nulo si no se asigna rol.
    @Column
    private String role;

    // Constructor vacío obligatorio para que JPA pueda instanciar la clase
    public User() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
