package com.yeyintlwin.musicsstore.api.controller;

import com.yeyintlwin.musicsstore.api.entity.Artist;
import com.yeyintlwin.musicsstore.api.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
@Tag(name = "Artist", description = "Artist management APIs")
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    @Operation(summary = "Get all artists", description = "Get a list of all artists in the store with optional pagination and search")
    public Page<Artist> getAllArtists(
            @Parameter(description = "Zero-based offset index") @RequestParam(required = false) Integer offset,
            @Parameter(description = "Maximum number of items to return") @RequestParam(required = false) Integer limit,
            @Parameter(description = "Search keyword for artist name") @RequestParam(required = false) String search) {
        return artistService.findAll(offset, limit, search);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an artist by ID")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        return artistService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new artist")
    public Artist createArtist(@RequestBody Artist artist) {
        return artistService.save(artist);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an artist by ID")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id, @RequestBody Artist artistDetails) {
        return artistService.findById(id)
                .map(existingArtist -> {
                    existingArtist.setName(artistDetails.getName());
                    Artist updatedArtist = artistService.save(existingArtist);
                    return ResponseEntity.ok(updatedArtist);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an artist by ID")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        return artistService.findById(id)
                .map(artist -> {
                    artistService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
