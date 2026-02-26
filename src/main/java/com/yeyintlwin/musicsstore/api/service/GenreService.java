package com.yeyintlwin.musicsstore.api.service;

import com.yeyintlwin.musicsstore.api.entity.Genre;
import com.yeyintlwin.musicsstore.api.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import com.yeyintlwin.musicsstore.api.util.OffsetPageRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public Page<Genre> findAll(Integer offset, Integer limit, String search) {
        org.springframework.data.domain.Pageable pageRequest;
        if (offset == null && limit == null) {
            pageRequest = org.springframework.data.domain.Pageable.unpaged();
        } else {
            int pageOffset = offset != null ? offset : 0;
            int pageLimit = limit != null ? limit : 10;
            pageRequest = new OffsetPageRequest(pageOffset, pageLimit);
        }

        if (search != null && !search.trim().isEmpty()) {
            return genreRepository.findByNameContainingIgnoreCase(search, pageRequest);
        }
        return genreRepository.findAll(pageRequest);
    }

    public Optional<Genre> findById(@NonNull Long id) {
        return genreRepository.findById(id);
    }

    public Genre save(@NonNull Genre genre) {
        return genreRepository.save(genre);
    }

    public void deleteById(@NonNull Long id) {
        genreRepository.deleteById(id);
    }
}
