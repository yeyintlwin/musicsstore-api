package com.yeyintlwin.musicsstore.api.repository;

import com.yeyintlwin.musicsstore.api.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Page<Music> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
