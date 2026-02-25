package com.yeyintlwin.musicsstore.api.controller;

import com.yeyintlwin.musicsstore.api.entity.Album;
import com.yeyintlwin.musicsstore.api.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
@Tag(name = "Album", description = "Album management APIs")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    @Operation(summary = "Get all albums", description = "Get a list of all albums in the store with optional pagination and search")
    public Page<Album> getAllAlbums(
            @Parameter(description = "Zero-based offset index") @RequestParam(required = false) Integer offset,
            @Parameter(description = "Maximum number of items to return") @RequestParam(required = false) Integer limit,
            @Parameter(description = "Search keyword for album name") @RequestParam(required = false) String search) {
        return albumService.findAll(offset, limit, search);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an album by ID")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
        return albumService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new album")
    public Album createAlbum(@RequestBody Album album) {
        return albumService.save(album);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an album by ID")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @RequestBody Album albumDetails) {
        return albumService.findById(id)
                .map(existingAlbum -> {
                    existingAlbum.setName(albumDetails.getName());
                    Album updatedAlbum = albumService.save(existingAlbum);
                    return ResponseEntity.ok(updatedAlbum);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an album by ID")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        return albumService.findById(id)
                .map(album -> {
                    albumService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
