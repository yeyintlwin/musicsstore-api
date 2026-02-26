package com.yeyintlwin.musicsstore.api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "musics")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Schema(description = "Represents a music track")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the music track", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Title of the music track", example = "Numb")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist")
    @Schema(description = "Artist who performed this track")
    private Artist artist;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre")
    @Schema(description = "Genre of the music track")
    private Genre genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "album")
    @Schema(description = "Album this track belongs to")
    private Album album;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country")
    @Schema(description = "Country of origin for this track")
    private Country country;

    @Column(length = 500)
    @Schema(description = "URL of the album/track cover image", example = "https://example.com/covers/numb.jpg")
    private String cover;

    @Schema(description = "Number of times this track has been played", example = "0")
    private Integer counter;

    @Column(length = 500)
    @Schema(description = "URL or path to the music file", example = "https://example.com/music/numb.mp3")
    private String link;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    @Schema(description = "Timestamp when the record was created", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date")
    @Schema(description = "Timestamp when the record was last modified", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime modifiedDate;
}
