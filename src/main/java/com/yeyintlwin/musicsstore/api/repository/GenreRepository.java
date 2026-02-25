package com.yeyintlwin.musicsstore.api.repository;

import com.yeyintlwin.musicsstore.api.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Page<Genre> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
