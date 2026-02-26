package com.yeyintlwin.musicsstore.api.controller;

import com.yeyintlwin.musicsstore.api.entity.Country;
import com.yeyintlwin.musicsstore.api.service.CountryService;
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
@RequestMapping("/api/countries")
@RequiredArgsConstructor
@Tag(name = "Country", description = "CRUD operations for countries")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    @Operation(summary = "Get all countries", description = "Returns a paginated list of countries. Use `offset` and `limit` for pagination, and `search` to filter by name.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of countries")
    })
    public Page<Country> getAllCountries(
            @Parameter(description = "Zero-based offset (starting record index). Default: 0") @RequestParam(required = false) Integer offset,
            @Parameter(description = "Maximum number of records to return. Default: 10") @RequestParam(required = false) Integer limit,
            @Parameter(description = "Keyword to search country name (case-insensitive)") @RequestParam(required = false) String search) {
        return countryService.findAll(offset, limit, search);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a country by ID", description = "Returns a single country by its unique ID. Returns 404 if not found.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Country found"),
            @ApiResponse(responseCode = "404", description = "Country not found")
    })
    public ResponseEntity<Country> getCountryById(
            @Parameter(description = "Unique ID of the country", required = true, example = "1") @PathVariable @NonNull Long id) {
        return countryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new country", description = "Creates a new country record. The `id`, `createdDate`, and `modifiedDate` fields are auto-generated and should not be included in the request body.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Country created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public Country createCountry(
            @Parameter(description = "Country data to create", required = true) @RequestBody @NonNull Country country) {
        return countryService.save(country);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a country by ID", description = "Updates an existing country's details. Only the `name` field is updated. Returns 404 if country does not exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Country updated successfully"),
            @ApiResponse(responseCode = "404", description = "Country not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<Country> updateCountry(
            @Parameter(description = "Unique ID of the country to update", required = true, example = "1") @PathVariable @NonNull Long id,
            @Parameter(description = "Updated country data", required = true) @RequestBody @NonNull Country countryDetails) {
        return countryService.findById(id)
                .map(existingCountry -> {
                    existingCountry.setName(countryDetails.getName());
                    Country updatedCountry = countryService.save(existingCountry);
                    return ResponseEntity.ok(updatedCountry);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a country by ID", description = "Permanently deletes a country record. Returns 404 if the country does not exist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Country deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Country not found")
    })
    public ResponseEntity<Void> deleteCountry(
            @Parameter(description = "Unique ID of the country to delete", required = true, example = "1") @PathVariable @NonNull Long id) {
        return countryService.findById(id)
                .map(country -> {
                    countryService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
