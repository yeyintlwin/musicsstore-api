package com.yeyintlwin.musicsstore.api.controller;

import com.yeyintlwin.musicsstore.api.entity.Genre;
import com.yeyintlwin.musicsstore.api.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
@Tag(name = "Genre", description = "CRUD operations for music genres")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    @Operation(summary = "Get all genres", description = "Returns a paginated list of genres. Use `offset` and `limit` for pagination, and `search` to filter by name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of genres")
    })
    public Page<Genre> getAllGenres(
            @Parameter(description = "Zero-based offset (starting record index). Default: 0") @RequestParam(required = false) Integer offset,
            @Parameter(description = "Maximum number of records to return. Default: 10") @RequestParam(required = false) Integer limit,
            @Parameter(description = "Keyword to search genre name (case-insensitive)") @RequestParam(required = false) String search) {
        return genreService.findAll(offset, limit, search);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a genre by ID", description = "Returns a single genre by its unique ID. Returns 404 if not found.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Genre found"),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    public ResponseEntity<Genre> getGenreById(
            @Parameter(description = "Unique ID of the genre", required = true, example = "1") @PathVariable @NonNull Long id) {
        return genreService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new genre", description = "Creates a new genre record. The `id`, `createdDate`, and `modifiedDate` fields are auto-generated and should not be included in the request body.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Genre created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public Genre createGenre(
            @Parameter(description = "Genre data to create", required = true) @RequestBody @NonNull Genre genre) {
        return genreService.save(genre);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a genre by ID", description = "Updates an existing genre's details. Only the `name` field is updated. Returns 404 if genre does not exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Genre updated successfully"),
            @ApiResponse(responseCode = "404", description = "Genre not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<Genre> updateGenre(
            @Parameter(description = "Unique ID of the genre to update", required = true, example = "1") @PathVariable @NonNull Long id,
            @Parameter(description = "Updated genre data", required = true) @RequestBody @NonNull Genre genreDetails) {
        return genreService.findById(id)
                .map(existingGenre -> {
                    existingGenre.setName(genreDetails.getName());
                    Genre updatedGenre = genreService.save(existingGenre);
                    return ResponseEntity.ok(updatedGenre);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a genre by ID", description = "Permanently deletes a genre record. Returns 404 if the genre does not exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Genre deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    public ResponseEntity<Void> deleteGenre(
            @Parameter(description = "Unique ID of the genre to delete", required = true, example = "1") @PathVariable @NonNull Long id) {
        return genreService.findById(id)
                .map(genre -> {
                    genreService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
