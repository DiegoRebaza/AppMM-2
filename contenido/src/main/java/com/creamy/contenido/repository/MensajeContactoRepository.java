package com.creamy.contenido.repository;

import com.creamy.contenido.model.MensajeContacto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MensajeContactoRepository extends JpaRepository<MensajeContacto, Long> {
    List<MensajeContacto> findAllByOrderByFechaEnvioDesc();
}