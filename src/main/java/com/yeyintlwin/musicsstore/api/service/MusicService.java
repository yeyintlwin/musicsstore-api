package com.yeyintlwin.musicsstore.api.service;

import com.yeyintlwin.musicsstore.api.entity.Music;
import com.yeyintlwin.musicsstore.api.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import com.yeyintlwin.musicsstore.api.util.OffsetPageRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;

    public Page<Music> findAll(Integer offset, Integer limit, String search,
            Long albumId, Long artistId, Long countryId, Long genreId) {
        org.springframework.data.domain.Pageable pageRequest;
        if (offset == null && limit == null) {
            pageRequest = org.springframework.data.domain.Pageable.unpaged();
        } else {
            int pageOffset = offset != null ? offset : 0;
            int pageLimit = limit != null ? limit : 10;
            pageRequest = new OffsetPageRequest(pageOffset, pageLimit);
        }

        org.springframework.data.jpa.domain.Specification<Music> spec = (root, query, cb) -> {
            var predicates = new java.util.ArrayList<jakarta.persistence.criteria.Predicate>();

            if (search != null && !search.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + search.toLowerCase() + "%"));
            }
            if (albumId != null) {
                predicates.add(cb.equal(root.get("album").get("id"), albumId));
            }
            if (artistId != null) {
                predicates.add(cb.equal(root.get("artist").get("id"), artistId));
            }
            if (countryId != null) {
                predicates.add(cb.equal(root.get("country").get("id"), countryId));
            }
            if (genreId != null) {
                predicates.add(cb.equal(root.get("genre").get("id"), genreId));
            }

            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };

        return musicRepository.findAll(spec, pageRequest);
    }

    public Optional<Music> findById(@NonNull Long id) {
        return musicRepository.findById(id);
    }

    public Music save(@NonNull Music music) {
        return musicRepository.save(music);
    }

    public void deleteById(@NonNull Long id) {
        musicRepository.deleteById(id);
    }
}
