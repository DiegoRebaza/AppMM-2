package com.creamy.compra.controller;

import com.creamy.compra.model.Encargo;
import com.creamy.compra.model.Pedido;
import com.creamy.compra.service.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    private EntityModel<Pedido> toPedidoModel(Pedido p) {
        return EntityModel.of(p,
                linkTo(methodOn(CompraController.class).obtenerPedido(p.getId())).withSelfRel(),
                linkTo(methodOn(CompraController.class).listarPorUsuario(p.getUsuarioEmail())).withRel("mis-compras"),
                linkTo(methodOn(CompraController.class).listarTodos()).withRel("todos-los-pedidos")
        );
    }

    private EntityModel<Encargo> toEncargoModel(Encargo e) {
        return EntityModel.of(e,
                linkTo(methodOn(CompraController.class).obtenerEncargo(e.getId())).withSelfRel(),
                linkTo(methodOn(CompraController.class).listarEncargos()).withRel("todos-los-encargos")
        );
    }

    // --- PEDIDOS ---
    @PostMapping
    public EntityModel<Pedido> registrarPedido(@Valid @RequestBody Pedido pedido) {
        return toPedidoModel(compraService.registrarPedido(pedido));
    }

    @GetMapping
    public CollectionModel<EntityModel<Pedido>> listarTodos() {
        List<EntityModel<Pedido>> lista = compraService.listarTodosLosPedidos().stream()
                .map(this::toPedidoModel)
                .toList();
        return CollectionModel.of(lista,
                linkTo(methodOn(CompraController.class).listarTodos()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Pedido> obtenerPedido(@PathVariable Long id) {
        return toPedidoModel(compraService.obtenerPedidoPorId(id));
    }

    @GetMapping("/usuario/{email}")
    public CollectionModel<EntityModel<Pedido>> listarPorUsuario(@PathVariable String email) {
        List<EntityModel<Pedido>> lista = compraService.listarPedidosPorUsuario(email).stream()
                .map(this::toPedidoModel)
                .toList();
        return CollectionModel.of(lista,
                linkTo(methodOn(CompraController.class).listarPorUsuario(email)).withSelfRel());
    }

    // --- ENCARGOS ---
    @PostMapping("/encargos")
    public EntityModel<Encargo> crearEncargo(@Valid @RequestBody Encargo encargo) {
        return toEncargoModel(compraService.crearEncargo(encargo));
    }

    @GetMapping("/encargos")
    public CollectionModel<EntityModel<Encargo>> listarEncargos() {
        List<EntityModel<Encargo>> lista = compraService.listarTodosLosEncargos().stream()
                .map(this::toEncargoModel)
                .toList();
        return CollectionModel.of(lista,
                linkTo(methodOn(CompraController.class).listarEncargos()).withSelfRel());
    }

    @GetMapping("/encargos/{id}")
    public EntityModel<Encargo> obtenerEncargo(@PathVariable Long id) {
        return toEncargoModel(compraService.obtenerEncargoPorId(id));
    }
}