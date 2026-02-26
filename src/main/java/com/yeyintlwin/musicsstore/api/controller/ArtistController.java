package com.yeyintlwin.musicsstore.api.controller;

import com.yeyintlwin.musicsstore.api.entity.Artist;
import com.yeyintlwin.musicsstore.api.service.ArtistService;
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
@RequestMapping("/api/artists")
@RequiredArgsConstructor
@Tag(name = "Artist", description = "CRUD operations for music artists")
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    @Operation(summary = "Get all artists", description = "Returns a paginated list of artists. Use `offset` and `limit` for pagination, and `search` to filter by name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of artists")
    })
    public Page<Artist> getAllArtists(
            @Parameter(description = "Zero-based offset (starting record index). Default: 0") @RequestParam(required = false) Integer offset,
            @Parameter(description = "Maximum number of records to return. Default: 10") @RequestParam(required = false) Integer limit,
            @Parameter(description = "Keyword to search artist name (case-insensitive)") @RequestParam(required = false) String search) {
        return artistService.findAll(offset, limit, search);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an artist by ID", description = "Returns a single artist by their unique ID. Returns 404 if not found.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Artist found"),
            @ApiResponse(responseCode = "404", description = "Artist not found")
    })
    public ResponseEntity<Artist> getArtistById(
            @Parameter(description = "Unique ID of the artist", required = true, example = "1") @PathVariable @NonNull Long id) {
        return artistService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new artist", description = "Creates a new artist record. The `id`, `createdDate`, and `modifiedDate` fields are auto-generated and should not be included in the request body.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Artist created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public Artist createArtist(
            @Parameter(description = "Artist data to create", required = true) @RequestBody @NonNull Artist artist) {
        return artistService.save(artist);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an artist by ID", description = "Updates an existing artist's details. Only the `name` field is updated. Returns 404 if artist does not exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Artist updated successfully"),
            @ApiResponse(responseCode = "404", description = "Artist not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<Artist> updateArtist(
            @Parameter(description = "Unique ID of the artist to update", required = true, example = "1") @PathVariable @NonNull Long id,
            @Parameter(description = "Updated artist data", required = true) @RequestBody @NonNull Artist artistDetails) {
        return artistService.findById(id)
                .map(existingArtist -> {
                    existingArtist.setName(artistDetails.getName());
                    Artist updatedArtist = artistService.save(existingArtist);
                    return ResponseEntity.ok(updatedArtist);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an artist by ID", description = "Permanently deletes an artist record. Returns 404 if the artist does not exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Artist deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Artist not found")
    })
    public ResponseEntity<Void> deleteArtist(
            @Parameter(description = "Unique ID of the artist to delete", required = true, example = "1") @PathVariable @NonNull Long id) {
        return artistService.findById(id)
                .map(artist -> {
                    artistService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
