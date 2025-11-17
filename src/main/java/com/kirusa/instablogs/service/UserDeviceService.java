package com.kirusa.instablogs.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kirusa.instablogs.model.Blogger;
import com.kirusa.instablogs.model.PendingRegistration;
import com.kirusa.instablogs.model.UserDevice;
import com.kirusa.instablogs.repository.UserDeviceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDeviceService {

    private final UserDeviceRepository userDeviceRepository;

    @Transactional
    public UserDevice createOrUpdate(Blogger blogger, PendingRegistration reg) {
        var device = userDeviceRepository
                .findByIvUserIdAndIvDeviceId(blogger.getBloggerId(), reg.getDeviceId())
                .orElseGet(() -> new UserDevice());

        device.setIvUserId(blogger.getBloggerId());
        device.setIvDeviceId(reg.getDeviceId());
        device.setDeviceModel(reg.getDeviceModel());
        device.setOsType(reg.getOsType());
        device.setOsVer(reg.getOsVer());
        device.setCountryCode(reg.getCountryCode());
        device.setStatus("u");
        device.setLastAccessed(java.time.LocalDateTime.now());
        // ✅ FIX: If creationDate is null (new record), set it now
        if (device.getCreationDate() == null) {
            device.setCreationDate(new Date());
        }
        return userDeviceRepository.save(device);
    }
}
