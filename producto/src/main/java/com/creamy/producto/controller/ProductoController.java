package com.creamy.producto.controller;

import com.creamy.producto.model.Producto;
import com.creamy.producto.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    private EntityModel<Producto> toModel(Producto p) {
        EntityModel<Producto> model = EntityModel.of(p,
                linkTo(methodOn(ProductoController.class).obtenerPorId(p.getId())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listarTodos()).withRel("productos"),
                linkTo(methodOn(ProductoController.class).listarPorCategoria(p.getCategoria())).withRel("por-categoria")
        );
        if (Boolean.TRUE.equals(p.getPersonalizado())) {
            model.add(linkTo(methodOn(ProductoController.class).eliminar(p.getId())).withRel("eliminar"));
        }
        return model;
    }

    @GetMapping
    public CollectionModel<EntityModel<Producto>> listarTodos() {
        List<EntityModel<Producto>> lista = productoService.listarTodos().stream()
                .map(this::toModel)
                .toList();
        return CollectionModel.of(lista,
                linkTo(methodOn(ProductoController.class).listarTodos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Producto> obtenerPorId(@PathVariable Long id) {
        return toModel(productoService.obtenerPorId(id));
    }

    @GetMapping("/categoria/{categoria}")
    public CollectionModel<EntityModel<Producto>> listarPorCategoria(@PathVariable String categoria) {
        List<EntityModel<Producto>> lista = productoService.listarPorCategoria(categoria).stream()
                .map(this::toModel)
                .toList();
        return CollectionModel.of(lista,
                linkTo(methodOn(ProductoController.class).listarPorCategoria(categoria)).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listarTodos()).withRel("productos"));
    }

    @PostMapping
    public EntityModel<Producto> crear(@Valid @RequestBody Producto producto) {
        return toModel(productoService.guardar(producto));
    }

    @PatchMapping("/{id}")
    public EntityModel<Producto> actualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        return toModel(productoService.actualizarParcial(id, campos));
    }

    @PostMapping("/restablecer")
    public CollectionModel<EntityModel<Producto>> restablecer() {
        List<EntityModel<Producto>> lista = productoService.restablecerPrecios().stream()
                .map(this::toModel)
                .toList();
        return CollectionModel.of(lista,
                linkTo(methodOn(ProductoController.class).listarTodos()).withRel("productos"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}