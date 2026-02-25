package com.yeyintlwin.musicsstore.api.controller;

import com.yeyintlwin.musicsstore.api.entity.Music;
import com.yeyintlwin.musicsstore.api.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/musics")
@RequiredArgsConstructor
@Tag(name = "Music", description = "Music management APIs")
public class MusicController {

    private final MusicService musicService;

    @GetMapping
    @Operation(summary = "Get all musics", description = "Get a list of all musics in the store with optional pagination and search")
    public Page<Music> getAllMusics(
            @Parameter(description = "Zero-based offset index") @RequestParam(required = false) Integer offset,
            @Parameter(description = "Maximum number of items to return") @RequestParam(required = false) Integer limit,
            @Parameter(description = "Search keyword for music title") @RequestParam(required = false) String search) {
        return musicService.findAll(offset, limit, search);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a music by ID")
    public ResponseEntity<Music> getMusicById(@PathVariable Long id) {
        return musicService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new music track")
    public Music createMusic(@RequestBody Music music) {
        return musicService.save(music);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a music track by ID")
    public ResponseEntity<Music> updateMusic(@PathVariable Long id, @RequestBody Music musicDetails) {
        return musicService.findById(id)
                .map(existingMusic -> {
                    existingMusic.setTitle(musicDetails.getTitle());
                    existingMusic.setArtist(musicDetails.getArtist());
                    existingMusic.setGenre(musicDetails.getGenre());
                    existingMusic.setAlbum(musicDetails.getAlbum());
                    existingMusic.setCountry(musicDetails.getCountry());
                    existingMusic.setCover(musicDetails.getCover());
                    existingMusic.setCounter(musicDetails.getCounter());
                    existingMusic.setLink(musicDetails.getLink());
                    Music updatedMusic = musicService.save(existingMusic);
                    return ResponseEntity.ok(updatedMusic);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a music track by ID")
    public ResponseEntity<Void> deleteMusic(@PathVariable Long id) {
        return musicService.findById(id)
                .map(music -> {
                    musicService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
