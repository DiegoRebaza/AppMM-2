package com.creamy.producto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "productos")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 60, message = "El nombre debe tener entre 3 y 60 caracteres")
    @Column(nullable = false, length = 150)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String detalle;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    @Max(value = 1000000, message = "El precio no puede superar 1.000.000")
    @Column(nullable = false)
    private Integer precio;

    @Column(name = "precio_original", nullable = false)
    private Integer precioOriginal;

    @Min(value = 0, message = "El descuento mínimo es 0%")
    @Max(value = 90, message = "El descuento máximo es 90%")
    private Integer descuento = 0;

    @NotBlank(message = "La imagen es obligatoria")
    private String imagen;

    private String alt;

    @NotBlank(message = "La categoría es obligatoria")
    @Column(nullable = false, length = 100)
    private String categoria;

    @Column(length = 50)
    private String tag;

    private Boolean personalizado = false;
}