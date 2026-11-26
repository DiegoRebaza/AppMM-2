package com.creamy.compra.repository;

import com.creamy.compra.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioEmailOrderByFechaDesc(String usuarioEmail);
    List<Pedido> findAllByOrderByFechaDesc();
}