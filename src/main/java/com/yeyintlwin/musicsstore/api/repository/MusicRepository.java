package com.yeyintlwin.musicsstore.api.repository;

import com.yeyintlwin.musicsstore.api.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MusicRepository extends JpaRepository<Music, Long>, JpaSpecificationExecutor<Music> {
    Page<Music> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
