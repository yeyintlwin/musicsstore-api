package com.yeyintlwin.musicsstore.api.repository;

import com.yeyintlwin.musicsstore.api.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Page<Album> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
