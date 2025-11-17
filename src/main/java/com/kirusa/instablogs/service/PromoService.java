package com.kirusa.instablogs.service;

import org.springframework.stereotype.Service;

import com.kirusa.instablogs.config.AppConfig;
import com.kirusa.instablogs.model.Blogger;
import com.kirusa.instablogs.util.CryptoUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PromoService {

    private final AppConfig appConfig;

    /**
     * Builds the final invite URL (modernized from old Common class)
     */
    public String getInviteUrl(Blogger blogger) {
        if (blogger == null || blogger.getBloggerId() == null)
            return null;

        String invType = "IVPromotion"; // you can pass dynamically if needed
        String key = generateINVMsgKey(invType, blogger.getBloggerId());

        return appConfig.getServiceBaseURL() + "invfriend/" + key;
    }

    private String generateINVMsgKey(String invType, Long ivUserId) {
        String raw = invType + ":" + ivUserId + ":" + System.currentTimeMillis();
        return CryptoUtil.encrypt(raw);
    }
}
