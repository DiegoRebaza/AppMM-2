package com.creamy.compra.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "encargos")
@Data
public class Encargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String correo;

    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombre;

    @NotBlank(message = "El teléfono o correo de contacto es obligatorio")
    private String contacto;

    @NotBlank(message = "Debe seleccionar una torta")
    private String torta;

    @NotNull(message = "La cantidad de pisos es obligatoria")
    @Min(value = 1, message = "Mínimo 1 piso")
    @Max(value = 3, message = "Máximo 3 pisos")
    private Integer pisos;

    @NotNull(message = "La cantidad de personas es obligatoria")
    @Min(value = 4, message = "Mínimo 4 personas")
    private Integer personas;

    @NotBlank(message = "El sabor es obligatorio")
    private String sabor;

    @Column(columnDefinition = "TEXT")
    private String quitar;

    @NotNull(message = "La fecha de entrega es obligatoria")
    @Future(message = "La fecha de entrega debe ser futura")
    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fechaEntrega;

    @Column(columnDefinition = "TEXT")
    private String diseno;

    @Column(columnDefinition = "LONGTEXT")
    private String foto;

    private String estado = "Pendiente";
    private LocalDateTime creado = LocalDateTime.now();
}