package com.creamy.producto.service;

import com.creamy.producto.model.Producto;
import com.creamy.producto.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public List<Producto> listarPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public Producto guardar(Producto producto) {
        if (producto.getPrecioOriginal() == null) {
            producto.setPrecioOriginal(producto.getPrecio());
        }
        if (producto.getDescuento() == null) {
            producto.setDescuento(0);
        }
        int precioFinal = (int) Math.round(producto.getPrecioOriginal() * (1 - producto.getDescuento() / 100.0));
        producto.setPrecio(precioFinal);
        return productoRepository.save(producto);
    }

    public Producto actualizarParcial(Long id, Map<String, Object> campos) {
        Producto existente = obtenerPorId(id);
        if (campos.containsKey("precio")) {
            existente.setPrecioOriginal(Integer.parseInt(campos.get("precio").toString()));
        }
        if (campos.containsKey("descuento")) {
            existente.setDescuento(Integer.parseInt(campos.get("descuento").toString()));
        }
        return guardar(existente);
    }

    public List<Producto> restablecerPrecios() {
        List<Producto> todos = productoRepository.findAll();
        todos.forEach(p -> {
            p.setDescuento(0);
            p.setPrecio(p.getPrecioOriginal());
        });
        return productoRepository.saveAll(todos);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}