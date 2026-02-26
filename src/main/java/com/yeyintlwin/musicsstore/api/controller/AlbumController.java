package com.yeyintlwin.musicsstore.api.controller;

import com.yeyintlwin.musicsstore.api.entity.Album;
import com.yeyintlwin.musicsstore.api.service.AlbumService;
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
@RequestMapping("/api/albums")
@RequiredArgsConstructor
@Tag(name = "Album", description = "CRUD operations for music albums")
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    @Operation(summary = "Get all albums", description = "Returns a paginated list of albums. Use `offset` and `limit` for pagination, and `search` to filter by name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of albums")
    })
    public Page<Album> getAllAlbums(
            @Parameter(description = "Zero-based offset (starting record index). Default: 0") @RequestParam(required = false) Integer offset,
            @Parameter(description = "Maximum number of records to return. Default: 10") @RequestParam(required = false) Integer limit,
            @Parameter(description = "Keyword to search album name (case-insensitive)") @RequestParam(required = false) String search) {
        return albumService.findAll(offset, limit, search);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an album by ID", description = "Returns a single album by its unique ID. Returns 404 if not found.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Album found"),
            @ApiResponse(responseCode = "404", description = "Album not found")
    })
    public ResponseEntity<Album> getAlbumById(
            @Parameter(description = "Unique ID of the album", required = true, example = "1") @PathVariable @NonNull Long id) {
        return albumService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new album", description = "Creates a new album record. The `id`, `createdDate`, and `modifiedDate` fields are auto-generated and should not be included in the request body.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Album created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public Album createAlbum(
            @Parameter(description = "Album data to create", required = true) @RequestBody @NonNull Album album) {
        return albumService.save(album);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an album by ID", description = "Updates an existing album's details. Only the `name` field is updated. Returns 404 if album does not exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Album updated successfully"),
            @ApiResponse(responseCode = "404", description = "Album not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<Album> updateAlbum(
            @Parameter(description = "Unique ID of the album to update", required = true, example = "1") @PathVariable @NonNull Long id,
            @Parameter(description = "Updated album data", required = true) @RequestBody @NonNull Album albumDetails) {
        return albumService.findById(id)
                .map(existingAlbum -> {
                    existingAlbum.setName(albumDetails.getName());
                    Album updatedAlbum = albumService.save(existingAlbum);
                    return ResponseEntity.ok(updatedAlbum);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an album by ID", description = "Permanently deletes an album record. Returns 404 if the album does not exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Album deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Album not found")
    })
    public ResponseEntity<Void> deleteAlbum(
            @Parameter(description = "Unique ID of the album to delete", required = true, example = "1") @PathVariable @NonNull Long id) {
        return albumService.findById(id)
                .map(album -> {
                    albumService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
