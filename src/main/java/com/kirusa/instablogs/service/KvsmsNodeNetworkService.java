package com.kirusa.instablogs.service;

import org.springframework.stereotype.Service;
import com.kirusa.instablogs.model.Country;
import com.kirusa.instablogs.model.KvsmsNodeNetwork;
import com.kirusa.instablogs.repository.KvsmsNodeNetworkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KvsmsNodeNetworkService {

    private final KvsmsNodeNetworkRepository repo;

    public KvsmsNodeNetwork getByMccMnc(String mccMnc) {
        if (mccMnc == null || mccMnc.isBlank()) return null;
        try {
            return repo.findByMccMnc(mccMnc);
        } catch (Exception e) {
            log.warn("MCC-MNC lookup failed [{}]: {}", mccMnc, e.getMessage());
            return null;
        }
    }

    public KvsmsNodeNetwork getByCountry(Country country) {
        return (country == null) ? null : getByMccMnc(country.getMccMnc());
    }
}
