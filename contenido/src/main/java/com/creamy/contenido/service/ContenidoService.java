package com.creamy.contenido.service;

import com.creamy.contenido.exception.ResourceNotFoundException;
import com.creamy.contenido.model.Blog;
import com.creamy.contenido.model.MensajeContacto;
import com.creamy.contenido.repository.BlogRepository;
import com.creamy.contenido.repository.MensajeContactoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContenidoService {

    private final BlogRepository blogRepository;
    private final MensajeContactoRepository mensajeRepository;

    // Blog
    public List<Blog> listarBlogs() {
        return blogRepository.findAllByOrderByFechaDesc();
    }

    public Blog obtenerBlogPorId(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artículo de blog no encontrado con id: " + id));
    }

    public List<Blog> listarBlogsPorCategoria(String categoria) {
        return blogRepository.findByCategoriaOrderByFechaDesc(categoria);
    }

    @Transactional
    public Blog crearBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    // Contacto
    @Transactional
    public MensajeContacto guardarMensaje(MensajeContacto mensaje) {
        return mensajeRepository.save(mensaje);
    }

    public List<MensajeContacto> listarMensajes() {
        return mensajeRepository.findAllByOrderByFechaEnvioDesc();
    }

    public MensajeContacto obtenerMensajePorId(Long id) {
        return mensajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mensaje no encontrado con id: " + id));
    }
}