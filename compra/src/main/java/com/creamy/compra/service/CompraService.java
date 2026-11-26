package com.creamy.compra.service;

import com.creamy.compra.model.Encargo;
import com.creamy.compra.model.Pedido;
import com.creamy.compra.repository.EncargoRepository;
import com.creamy.compra.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final PedidoRepository pedidoRepository;
    private final EncargoRepository encargoRepository;

    // --- PEDIDOS ---
    @Transactional
    public Pedido registrarPedido(Pedido pedido) {
        if ("despacho".equalsIgnoreCase(pedido.getTipoEntrega()) && "efectivo".equalsIgnoreCase(pedido.getMetodoPago())) {
            throw new IllegalArgumentException("El pago en efectivo solo está disponible para retiro en tienda.");
        }

        if (pedido.getNumero() == null || pedido.getNumero().isBlank()) {
            pedido.setNumero("CRM-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        }

        pedido.getItems().forEach(item -> item.setPedido(pedido));
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarTodosLosPedidos() {
        return pedidoRepository.findAllByOrderByFechaDesc();
    }

    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }

    public List<Pedido> listarPedidosPorUsuario(String email) {
        return pedidoRepository.findByUsuarioEmailOrderByFechaDesc(email);
    }

    // --- ENCARGOS ---
    @Transactional
    public Encargo crearEncargo(Encargo encargo) {
        return encargoRepository.save(encargo);
    }

    public List<Encargo> listarTodosLosEncargos() {
        return encargoRepository.findAllByOrderByCreadoDesc();
    }

    public Encargo obtenerEncargoPorId(Long id) {
        return encargoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encargo no encontrado con id: " + id));
    }
}