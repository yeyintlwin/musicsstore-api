package com.yeyintlwin.musicsstore.api.controller;

import com.yeyintlwin.musicsstore.api.entity.Country;
import com.yeyintlwin.musicsstore.api.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
@Tag(name = "Country", description = "Country management APIs")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    @Operation(summary = "Get all countries", description = "Get a list of all countries in the store with optional pagination and search")
    public Page<Country> getAllCountries(
            @Parameter(description = "Zero-based offset index") @RequestParam(required = false) Integer offset,
            @Parameter(description = "Maximum number of items to return") @RequestParam(required = false) Integer limit,
            @Parameter(description = "Search keyword for country name") @RequestParam(required = false) String search) {
        return countryService.findAll(offset, limit, search);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a country by ID")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        return countryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new country")
    public Country createCountry(@RequestBody Country country) {
        return countryService.save(country);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a country by ID")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody Country countryDetails) {
        return countryService.findById(id)
                .map(existingCountry -> {
                    existingCountry.setName(countryDetails.getName());
                    Country updatedCountry = countryService.save(existingCountry);
                    return ResponseEntity.ok(updatedCountry);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a country by ID")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        return countryService.findById(id)
                .map(country -> {
                    countryService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
