package com.yeyintlwin.musicsstore.api.service;

import com.yeyintlwin.musicsstore.api.entity.Album;
import com.yeyintlwin.musicsstore.api.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import com.yeyintlwin.musicsstore.api.util.OffsetPageRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    public Page<Album> findAll(Integer offset, Integer limit, String search) {
        int pageOffset = offset != null ? offset : 0;
        int pageLimit = limit != null ? limit : Integer.MAX_VALUE;
        OffsetPageRequest pageRequest = new OffsetPageRequest(pageOffset, pageLimit);
        if (search != null && !search.trim().isEmpty()) {
            return albumRepository.findByNameContainingIgnoreCase(search, pageRequest);
        }
        return albumRepository.findAll(pageRequest);
    }

    public Optional<Album> findById(Long id) {
        return albumRepository.findById(id);
    }

    public Album save(Album album) {
        return albumRepository.save(album);
    }

    public void deleteById(Long id) {
        albumRepository.deleteById(id);
    }
}
