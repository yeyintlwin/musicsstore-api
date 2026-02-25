package com.yeyintlwin.musicsstore.api.service;

import com.yeyintlwin.musicsstore.api.entity.Country;
import com.yeyintlwin.musicsstore.api.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import com.yeyintlwin.musicsstore.api.util.OffsetPageRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public Page<Country> findAll(Integer offset, Integer limit, String search) {
        int pageOffset = offset != null ? offset : 0;
        int pageLimit = limit != null ? limit : Integer.MAX_VALUE;
        OffsetPageRequest pageRequest = new OffsetPageRequest(pageOffset, pageLimit);
        if (search != null && !search.trim().isEmpty()) {
            return countryRepository.findByNameContainingIgnoreCase(search, pageRequest);
        }
        return countryRepository.findAll(pageRequest);
    }

    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    public Country save(Country country) {
        return countryRepository.save(country);
    }

    public void deleteById(Long id) {
        countryRepository.deleteById(id);
    }
}
