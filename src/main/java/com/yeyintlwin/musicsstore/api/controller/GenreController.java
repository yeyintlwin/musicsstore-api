package com.yeyintlwin.musicsstore.api.controller;

import com.yeyintlwin.musicsstore.api.entity.Genre;
import com.yeyintlwin.musicsstore.api.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
@Tag(name = "Genre", description = "Genre management APIs")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    @Operation(summary = "Get all genres", description = "Get a list of all genres in the store with optional pagination and search")
    public Page<Genre> getAllGenres(
            @Parameter(description = "Zero-based offset index") @RequestParam(required = false) Integer offset,
            @Parameter(description = "Maximum number of items to return") @RequestParam(required = false) Integer limit,
            @Parameter(description = "Search keyword for genre name") @RequestParam(required = false) String search) {
        return genreService.findAll(offset, limit, search);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a genre by ID")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        return genreService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new genre")
    public Genre createGenre(@RequestBody Genre genre) {
        return genreService.save(genre);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a genre by ID")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @RequestBody Genre genreDetails) {
        return genreService.findById(id)
                .map(existingGenre -> {
                    existingGenre.setName(genreDetails.getName());
                    Genre updatedGenre = genreService.save(existingGenre);
                    return ResponseEntity.ok(updatedGenre);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a genre by ID")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        return genreService.findById(id)
                .map(genre -> {
                    genreService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
