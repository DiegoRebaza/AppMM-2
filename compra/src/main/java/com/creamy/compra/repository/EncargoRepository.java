package com.creamy.compra.repository;

import com.creamy.compra.model.Encargo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EncargoRepository extends JpaRepository<Encargo, Long> {
    List<Encargo> findByCorreoOrderByCreadoDesc(String correo);
    List<Encargo> findAllByOrderByCreadoDesc();
}