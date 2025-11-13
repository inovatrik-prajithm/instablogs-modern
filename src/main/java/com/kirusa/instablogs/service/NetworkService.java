package com.kirusa.instablogs.service;

import org.springframework.stereotype.Service;
import com.kirusa.instablogs.model.Country;
import com.kirusa.instablogs.model.KvsmsNodeNetwork;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NetworkService {

    private final KvsmsNodeNetworkService nodeService;

    public KvsmsNodeNetwork resolveNodeNetwork(String simNet, String simOpr, Country country, Boolean phoneEdited) {
        try {
            if (!Boolean.TRUE.equals(phoneEdited) && valid(simNet))
                return nodeService.getByMccMnc(simNet);
            if (valid(simOpr))
                return nodeService.getByMccMnc(simOpr);
        } catch (Exception e) {
            log.warn("Network resolve failed: {}", e.getMessage());
        }
        return nodeService.getByCountry(country);
    }

    private boolean valid(String s) {
        return s != null && !s.isBlank();
    }
}
