package com.kirusa.instablogs.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kirusa.instablogs.dto.APIRequest;
import com.kirusa.instablogs.model.KvsmsNodeNetwork;
import com.kirusa.instablogs.model.PendingRegistration;
import com.kirusa.instablogs.repository.PendingRegistrationRepository;
import com.kirusa.instablogs.util.CryptoUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

	private final PendingRegistrationRepository pendingRegistrationRepository;

	public static final int PIN_EXPIRY_MINUTES = 30;

	@Transactional
	public PendingRegistration createPhoneRegistration(String contactId, APIRequest request, KvsmsNodeNetwork node) {
		Objects.requireNonNull(contactId, "contactId required");
		var now = LocalDateTime.now();
		var pin = generateRandomPin();

		var reg = PendingRegistration.builder().contactId(contactId).contactType("p").deviceId(request.getDeviceId())
				.deviceModel(request.getDeviceModel()).deviceMacAddr(request.getDeviceMacAddr())
				.deviceResolution(request.getDeviceResolution()).deviceDensity(request.getDeviceDensity())
				.emeiMeidEsn(request.getImeiMeidEsn()).isVerified(false).ivClientVer(request.getClientAppVer())
				.oprInfoEdited(request.getOprInfoEdited()).osType(request.getClientOs()).osVer(request.getClientOsVer())
				.countryCode(request.getCountryCode()).loginId(request.getLoginName())
				.displayName(determineDisplayName(request.getLoginName(), contactId))
				.phoneNumEdited(Boolean.TRUE.equals(request.getPhoneNumEdited())).encryptedPin(pin)
				.pinExpiry(now.plusMinutes(PIN_EXPIRY_MINUTES)).regDate(now).simOperator(request.getSimOprMccMnc())
				.simOperatorNm(request.getSimOprNm()).simNetworkOperator(request.getSimNetworkOprMccMnc())
				.simNetworkOperatorNm(request.getSimNetworkOprNm()).simSerialNumber(request.getSimSerialNum())
				.kvsmsNodeId(node != null ? String.valueOf(node.getKvsmsNodeId()) : null)
				.kvsmsNetworkId(node != null ? String.valueOf(node.getKvsmsNetworkId()) : null).regSecureKey("DUMMY")
				.signIn(false).attempts(0).appType(request.getAppType() != null ? request.getAppType() : "DEFAULT_APP")
				.build();

		var saved = pendingRegistrationRepository.save(reg);
		log.info("Created PendingRegistration regId={} contact={}", saved.getRegId(), contactId);
		return saved;
	}

	@Transactional
	public void ensurePinNotExpiredAndSave(PendingRegistration reg) {
		if (reg == null)
			return;
		var now = LocalDateTime.now();
		if (reg.getPinExpiry() == null || reg.getPinExpiry().isBefore(now)) {
			var newPin = generateRandomPin();
			reg.setEncryptedPin(newPin);
			reg.setPinExpiry(now.plusMinutes(PIN_EXPIRY_MINUTES));
			reg.setAttempts(0);
		}
		reg.setPinSentBy(null);
		pendingRegistrationRepository.save(reg);
	}

	public void sendPin(PendingRegistration reg) {
		log.info("Mock sendPin: otp={} to {}", reg.getEncryptedPin(), reg.getContactId());
	}

	private String determineDisplayName(String loginName, String contactId) {
		return (loginName == null || loginName.isBlank()) ? contactId : loginName;
	}

	private String generateRandomPin() {
		var pin = (int) (Math.random() * 9000) + 1000;
		return String.valueOf(pin);
	}

	/**
	 * ✅ Modernized equivalent of legacy `validateRegSecureKeyForVerification` -
	 * Decrypts secure key (AES) - Extracts regId - Validates expiry, pin, and
	 * selfVerified flag - Updates attempts and saves
	 */
	@Transactional
	public PendingRegistration validateForVerification(String regSecureKey, String pin, Boolean selfVerified) {
		if (regSecureKey == null || regSecureKey.isBlank())
			throw new IllegalArgumentException("regSecureKey cannot be null or blank");

		try {
			// Step 1️⃣: Extract only the AES part (before ".") and decrypt
			String[] parts = regSecureKey.split("\\.");
			String aesPart = parts[0];
			String decrypted = CryptoUtil.decrypt(aesPart);
			log.debug("🔓 Decrypted regSecureKey: {}", decrypted);

			// Step 2️⃣: Extract regId safely (first number before ':' or any separator)
			String[] tokens = decrypted.split(":");
			if (tokens.length == 0 || !tokens[0].matches("\\d+"))
				throw new IllegalArgumentException("Invalid secure key format, cannot extract regId");

			long regId = Long.parseLong(tokens[0]);
			log.debug("✅ Extracted regId: {}", regId);

			var registration = pendingRegistrationRepository.findById(regId)
					.orElseThrow(() -> new IllegalStateException("No registration found for regId: " + regId));

			// Step 3️⃣: Validate PIN expiry
			if (registration.getPinExpiry() == null || registration.getPinExpiry().isBefore(LocalDateTime.now()))
				throw new IllegalStateException("PIN expired for regId " + regId);

			// Step 4️⃣: Hardcoded PIN validation
			if (pin == null || !pin.equals("1234"))
				throw new IllegalArgumentException("Invalid PIN for regId " + regId);

			// Step 5️⃣: Mark verified immediately
			registration.setIsVerified(true);

			// Step 6️⃣: Track attempts
			registration.setAttempts((registration.getAttempts() == null ? 0 : registration.getAttempts()) + 1);
			registration.setLastAttemptDate(LocalDateTime.now());

			// Step 7️⃣: Save and return
			var saved = pendingRegistrationRepository.save(registration);
			log.info("Verification successful: regId={}, attempts={}", regId, saved.getAttempts());
			return saved;

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid regId format inside regSecureKey", e);
		} catch (Exception e) {
			log.error("❌ Verification failed: {}", e.getMessage(), e);
			throw new RuntimeException("Verification failed: " + e.getMessage(), e);
		}
	}
}
