package com.creamy.contenido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes_contacto")
@Data
public class MensajeContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Ingresa un correo válido")
    @Size(max = 100, message = "El correo no puede superar los 100 caracteres")
    private String email;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 30, max = 500, message = "El mensaje debe tener entre 30 y 500 caracteres")
    private String mensaje;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio = LocalDateTime.now();
}