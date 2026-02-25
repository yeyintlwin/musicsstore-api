package com.yeyintlwin.musicsstore.api.service;

import com.yeyintlwin.musicsstore.api.entity.Genre;
import com.yeyintlwin.musicsstore.api.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import com.yeyintlwin.musicsstore.api.util.OffsetPageRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public Page<Genre> findAll(Integer offset, Integer limit, String search) {
        int pageOffset = offset != null ? offset : 0;
        int pageLimit = limit != null ? limit : Integer.MAX_VALUE;
        OffsetPageRequest pageRequest = new OffsetPageRequest(pageOffset, pageLimit);
        if (search != null && !search.trim().isEmpty()) {
            return genreRepository.findByNameContainingIgnoreCase(search, pageRequest);
        }
        return genreRepository.findAll(pageRequest);
    }

    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    public void deleteById(Long id) {
        genreRepository.deleteById(id);
    }
}
