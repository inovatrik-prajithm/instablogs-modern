package com.kirusa.instablogs.service.impl;

import java.time.Instant;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kirusa.instablogs.model.Invitation;
import com.kirusa.instablogs.repository.InvitationRepository;
import com.kirusa.instablogs.service.ConfigService;
import com.kirusa.instablogs.util.CryptoUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationServiceImpl {

	private final InvitationRepository invitationRepo;
	private final ConfigService configService;

	/**
	 * Generates an Invite URL and saves an Invitation entry in DB.
	 */
	@Transactional
	public String generateAndSaveInvite(Long fromUserId, String invType, Long deviceId, String sourceAppType) {
		// 🧩 Step 1: Build encrypted invite key (modern equivalent of Common.encrypt)
		String keyData = invType + ":" + fromUserId + ":" + Instant.now().toEpochMilli();
		String encryptedKey = CryptoUtil.encrypt(keyData);

		// 🧩 Step 2: Build final invite URL
		String baseUrl = configService.getServiceBaseUrl(); // http://localhost:8080/vobolo/
		String inviteUrl = baseUrl + "invfriend/" + encryptedKey;

		// 🧩 Step 3: Create or update invitation entity
		Invitation existing = invitationRepo.findByInviteUrl(inviteUrl).orElse(null);
		Invitation invitation = existing != null ? existing : new Invitation();

		invitation.setCreationDate(Calendar.getInstance());
		invitation.setByIvUserId(fromUserId);
		invitation.setInviteUrl(inviteUrl);
		invitation.setFromUserDeviceId(deviceId);
		invitation.setSourceAppType(sourceAppType);
		invitation.setReferralCode(generateReferralCode(fromUserId));

		invitationRepo.save(invitation);

		log.info("✅ Invitation created for user={} type={} url={}", fromUserId, invType, inviteUrl);
		return inviteUrl;
	}

	/**
	 * Generates a short referral code (legacy-compatible, 6-char).
	 */
	public String generateReferralCode(Long userId) {
	    String seed = userId + "-" + Instant.now().toEpochMilli();
	    String hash = Integer.toHexString(seed.hashCode()).toUpperCase();
	    return hash.substring(0, Math.min(hash.length(), 6));
	}
}
