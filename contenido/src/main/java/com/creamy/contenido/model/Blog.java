package com.creamy.contenido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "blogs")
@Data
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 5, max = 200, message = "El título debe tener entre 5 y 200 caracteres")
    private String titulo;

    @NotBlank(message = "El resumen es obligatorio")
    @Column(columnDefinition = "TEXT")
    private String resumen;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    private LocalDate fecha = LocalDate.now();

    @NotBlank(message = "El autor es obligatorio")
    private String autor;

    @NotNull(message = "Los minutos de lectura son obligatorios")
    @Min(value = 1, message = "Mínimo 1 minuto de lectura")
    private Integer minutos;

    private String imagen;
    private String alt;

    @NotEmpty(message = "El artículo debe tener al menos un párrafo de contenido")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "blog_parrafos", joinColumns = @JoinColumn(name = "blog_id"))
    @OrderColumn(name = "orden")
    @Column(name = "texto", columnDefinition = "TEXT")
    private List<String> contenido;
}