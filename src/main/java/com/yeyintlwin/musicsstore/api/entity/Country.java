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
@Table(name = "countries")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Schema(description = "Represents a country of origin for artists or music")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the country", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Name of the country", example = "Myanmar")
    private String name;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    @Schema(description = "Timestamp when the record was created", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "modified_date")
    @Schema(description = "Timestamp when the record was last modified", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime modifiedDate;
}
