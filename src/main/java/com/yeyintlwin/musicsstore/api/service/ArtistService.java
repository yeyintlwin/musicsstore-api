package com.yeyintlwin.musicsstore.api.service;

import com.yeyintlwin.musicsstore.api.entity.Artist;
import com.yeyintlwin.musicsstore.api.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import com.yeyintlwin.musicsstore.api.util.OffsetPageRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public Page<Artist> findAll(Integer offset, Integer limit, String search) {
        int pageOffset = offset != null ? offset : 0;
        int pageLimit = limit != null ? limit : Integer.MAX_VALUE;
        OffsetPageRequest pageRequest = new OffsetPageRequest(pageOffset, pageLimit);
        if (search != null && !search.trim().isEmpty()) {
            return artistRepository.findByNameContainingIgnoreCase(search, pageRequest);
        }
        return artistRepository.findAll(pageRequest);
    }

    public Optional<Artist> findById(Long id) {
        return artistRepository.findById(id);
    }

    public Artist save(Artist artist) {
        return artistRepository.save(artist);
    }

    public void deleteById(Long id) {
        artistRepository.deleteById(id);
    }
}
