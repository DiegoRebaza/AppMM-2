package com.creamy.compra.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String numero;

    @NotBlank(message = "El correo del usuario es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    @Column(name = "usuario_email", nullable = false, length = 150)
    private String usuarioEmail;

    @Column(name = "usuario_nombre", length = 150)
    private String usuarioNombre;

    @NotNull(message = "El subtotal es obligatorio")
    @Min(value = 0, message = "El subtotal no puede ser negativo")
    private Integer subtotal;

    @NotNull(message = "El costo de envío es obligatorio")
    @Min(value = 0, message = "El envío no puede ser negativo")
    private Integer envio;

    @NotNull(message = "El total es obligatorio")
    @Min(value = 1, message = "El total debe ser mayor a 0")
    private Integer total;

    @NotBlank(message = "El tipo de entrega es obligatorio")
    @Column(name = "tipo_entrega", nullable = false, length = 30)
    private String tipoEntrega; // retiro | despacho

    @Size(max = 300, message = "La dirección no puede superar los 300 caracteres")
    private String direccion;

    private String comuna;

    @NotBlank(message = "El método de pago es obligatorio")
    @Column(name = "metodo_pago", nullable = false, length = 30)
    private String metodoPago; // tarjeta | transferencia | efectivo

    @Size(max = 4, message = "Solo se permiten los últimos 4 dígitos")
    private String ultimos4;

    private String estado = "Confirmado";
    private LocalDateTime fecha = LocalDateTime.now();

    @NotEmpty(message = "El pedido debe contener al menos un producto")
    @Valid
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItem> items = new ArrayList<>();
}