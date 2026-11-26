package com.creamy.contenido.repository;

import com.creamy.contenido.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByFechaDesc();
    List<Blog> findByCategoriaOrderByFechaDesc(String categoria);
}