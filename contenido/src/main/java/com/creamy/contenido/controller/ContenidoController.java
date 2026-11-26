package com.creamy.contenido.controller;

import com.creamy.contenido.model.Blog;
import com.creamy.contenido.model.MensajeContacto;
import com.creamy.contenido.service.ContenidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/contenido")
@RequiredArgsConstructor
public class ContenidoController {

    private final ContenidoService contenidoService;

    private EntityModel<Blog> toBlogModel(Blog b) {
        return EntityModel.of(b,
                linkTo(methodOn(ContenidoController.class).obtenerBlog(b.getId())).withSelfRel(),
                linkTo(methodOn(ContenidoController.class).listarBlogs()).withRel("todos-los-blogs"),
                linkTo(methodOn(ContenidoController.class).listarBlogsPorCategoria(b.getCategoria())).withRel("por-categoria")
        );
    }

    private EntityModel<MensajeContacto> toMensajeModel(MensajeContacto m) {
        return EntityModel.of(m,
                linkTo(methodOn(ContenidoController.class).obtenerMensaje(m.getId())).withSelfRel(),
                linkTo(methodOn(ContenidoController.class).listarMensajes()).withRel("todos-los-mensajes")
        );
    }

    // --- BLOGS ---
    @GetMapping("/blogs")
    public CollectionModel<EntityModel<Blog>> listarBlogs() {
        List<EntityModel<Blog>> lista = contenidoService.listarBlogs().stream()
                .map(this::toBlogModel)
                .toList();
        return CollectionModel.of(lista,
                linkTo(methodOn(ContenidoController.class).listarBlogs()).withSelfRel());
    }

    @GetMapping("/blogs/{id}")
    public EntityModel<Blog> obtenerBlog(@PathVariable Long id) {
        return toBlogModel(contenidoService.obtenerBlogPorId(id));
    }

    @GetMapping("/blogs/categoria/{categoria}")
    public CollectionModel<EntityModel<Blog>> listarBlogsPorCategoria(@PathVariable String categoria) {
        List<EntityModel<Blog>> lista = contenidoService.listarBlogsPorCategoria(categoria).stream()
                .map(this::toBlogModel)
                .toList();
        return CollectionModel.of(lista,
                linkTo(methodOn(ContenidoController.class).listarBlogsPorCategoria(categoria)).withSelfRel(),
                linkTo(methodOn(ContenidoController.class).listarBlogs()).withRel("todos-los-blogs"));
    }

    @PostMapping("/blogs")
    public EntityModel<Blog> crearBlog(@Valid @RequestBody Blog blog) {
        return toBlogModel(contenidoService.crearBlog(blog));
    }

    // --- CONTACTO ---
    @PostMapping("/contacto")
    public EntityModel<MensajeContacto> enviarMensaje(@Valid @RequestBody MensajeContacto mensaje) {
        return toMensajeModel(contenidoService.guardarMensaje(mensaje));
    }

    @GetMapping("/contacto")
    public CollectionModel<EntityModel<MensajeContacto>> listarMensajes() {
        List<EntityModel<MensajeContacto>> lista = contenidoService.listarMensajes().stream()
                .map(this::toMensajeModel)
                .toList();
        return CollectionModel.of(lista,
                linkTo(methodOn(ContenidoController.class).listarMensajes()).withSelfRel());
    }

    @GetMapping("/contacto/{id}")
    public EntityModel<MensajeContacto> obtenerMensaje(@PathVariable Long id) {
        return toMensajeModel(contenidoService.obtenerMensajePorId(id));
    }
}