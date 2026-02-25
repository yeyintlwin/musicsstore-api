package com.yeyintlwin.musicsstore.api.service;

import com.yeyintlwin.musicsstore.api.entity.Music;
import com.yeyintlwin.musicsstore.api.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import com.yeyintlwin.musicsstore.api.util.OffsetPageRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;

    public Page<Music> findAll(Integer offset, Integer limit, String search) {
        int pageOffset = offset != null ? offset : 0;
        int pageLimit = limit != null ? limit : Integer.MAX_VALUE;
        OffsetPageRequest pageRequest = new OffsetPageRequest(pageOffset, pageLimit);
        if (search != null && !search.trim().isEmpty()) {
            return musicRepository.findByTitleContainingIgnoreCase(search, pageRequest);
        }
        return musicRepository.findAll(pageRequest);
    }

    public Optional<Music> findById(Long id) {
        return musicRepository.findById(id);
    }

    public Music save(Music music) {
        return musicRepository.save(music);
    }

    public void deleteById(Long id) {
        musicRepository.deleteById(id);
    }
}
