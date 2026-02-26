package com.yeyintlwin.musicsstore.api.controller;

import com.yeyintlwin.musicsstore.api.entity.Music;
import com.yeyintlwin.musicsstore.api.service.MusicService;
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
@RequestMapping("/api/musics")
@RequiredArgsConstructor
@Tag(name = "Music", description = "CRUD operations for music tracks")
public class MusicController {

        private final MusicService musicService;

        @GetMapping
        @Operation(summary = "Get all music tracks", description = "Returns a paginated list of music tracks. Use `offset` and `limit` for pagination, and `search` to filter by title. You can also filter by album, artist, country, or genre by passing their IDs.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of music tracks")
        })
        public Page<Music> getAllMusics(
                        @Parameter(description = "Zero-based offset (starting record index). Default: 0") @RequestParam(required = false) Integer offset,
                        @Parameter(description = "Maximum number of records to return. Default: 10") @RequestParam(required = false) Integer limit,
                        @Parameter(description = "Keyword to search music title (case-insensitive)") @RequestParam(required = false) String search,
                        @Parameter(description = "Filter by Album ID") @RequestParam(required = false) Long albumId,
                        @Parameter(description = "Filter by Artist ID") @RequestParam(required = false) Long artistId,
                        @Parameter(description = "Filter by Country ID") @RequestParam(required = false) Long countryId,
                        @Parameter(description = "Filter by Genre ID") @RequestParam(required = false) Long genreId) {
                return musicService.findAll(offset, limit, search, albumId, artistId, countryId, genreId);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get a music track by ID", description = "Returns a single music track by its unique ID. Returns 404 if not found.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Music track found"),
                        @ApiResponse(responseCode = "404", description = "Music track not found")
        })
        public ResponseEntity<Music> getMusicById(
                        @Parameter(description = "Unique ID of the music track", required = true, example = "1") @PathVariable @NonNull Long id) {
                return musicService.findById(id)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping
        @Operation(summary = "Create a new music track", description = "Creates a new music track. The `id`, `createdDate`, and `modifiedDate` are auto-generated. "
                        +
                        "For `artist`, `genre`, `album`, and `country`, provide an object with only the `id` field (e.g. `{\"id\": 1}`).")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Music track created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid request body")
        })
        public Music createMusic(
                        @Parameter(description = "Music track data to create", required = true) @RequestBody @NonNull Music music) {
                return musicService.save(music);
        }

        @PutMapping("/{id}")
        @Operation(summary = "Update a music track by ID", description = "Updates all fields of an existing music track: `title`, `artist`, `genre`, `album`, `country`, `cover`, `counter`, and `link`. Returns 404 if not found.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Music track updated successfully"),
                        @ApiResponse(responseCode = "404", description = "Music track not found"),
                        @ApiResponse(responseCode = "400", description = "Invalid request body")
        })
        public ResponseEntity<Music> updateMusic(
                        @Parameter(description = "Unique ID of the music track to update", required = true, example = "1") @PathVariable @NonNull Long id,
                        @Parameter(description = "Updated music track data", required = true) @RequestBody @NonNull Music musicDetails) {
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
        @Operation(summary = "Delete a music track by ID", description = "Permanently deletes a music track. Returns 404 if the track does not exist.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Music track deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Music track not found")
        })
        public ResponseEntity<Void> deleteMusic(
                        @Parameter(description = "Unique ID of the music track to delete", required = true, example = "1") @PathVariable @NonNull Long id) {
                return musicService.findById(id)
                                .map(music -> {
                                        musicService.deleteById(id);
                                        return ResponseEntity.ok().<Void>build();
                                })
                                .orElse(ResponseEntity.notFound().build());
        }
}
