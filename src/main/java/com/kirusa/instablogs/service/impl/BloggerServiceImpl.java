package com.kirusa.instablogs.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kirusa.instablogs.model.Blogger;
import com.kirusa.instablogs.model.PendingRegistration;
import com.kirusa.instablogs.repository.BloggerRepository;
import com.kirusa.instablogs.repository.UserDeviceRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BloggerServiceImpl {

	private final BloggerRepository bloggerRepository;
	private final UserDeviceRepository userDeviceRepository;

	/** ✅ Check if Blogger is IV user */
	public boolean isIVUser(Blogger blogger) {
		return blogger != null && blogger.getIvSignupDate() != null;
	}

	public boolean isRingUser(Long bloggerId) {
		return userDeviceRepository.anyRingDeviceExists(bloggerId);
	}

	public boolean isBlogsUser(Long bloggerId) {
		return userDeviceRepository.anyBlogsDeviceExists(bloggerId);
	}

	public boolean isReachMeUser(Long bloggerId) {
		return userDeviceRepository.anyReachMeDeviceExists(bloggerId);
	}

	/** ✅ Used from VerifyUserServiceImpl */
	@Transactional
	public Blogger createOrUpdateBlogger(PendingRegistration registration) {
		return createOrUpdateBlogger(registration, null, "G"); // “G” = Generic Blogger Type
	}

	/**
	 * ✅ Full Blogger creation/update logic Modernized but compatible with legacy
	 * Blogger entity fields.
	 */
	@Transactional
	public Blogger createOrUpdateBlogger(PendingRegistration registration, String cloudSecureKey, String bloggerType) {
		if (registration == null || registration.getContactId() == null) {
			throw new IllegalArgumentException("Registration or contactId cannot be null");
		}

		String contactId = registration.getContactId();
		String contactType = registration.getContactType();
		String sourceApp = registration.getAppType();

		// 🔍 Find existing blogger by phone or email
		Blogger blogger;

		if ("p".equalsIgnoreCase(contactType)) {
			blogger = bloggerRepository.findByPrimaryNumber(contactId).orElseGet(() -> {
				Blogger newBlogger = new Blogger();
				newBlogger.setStatus("A");
				newBlogger.setBloggerType(bloggerType);
				newBlogger.setLoginId(registration.getLoginId());
				newBlogger.setDisplayName(registration.getDisplayName());
				newBlogger.setCountryCode(registration.getCountryCode());
				newBlogger.setCreationDate(new java.util.Date());
				return newBlogger;
			});
		} else {
			blogger = bloggerRepository.findByEmail(contactId).orElseGet(() -> {
				Blogger newBlogger = new Blogger();
				newBlogger.setStatus("A");
				newBlogger.setBloggerType(bloggerType);
				newBlogger.setLoginId(registration.getLoginId());
				newBlogger.setDisplayName(registration.getDisplayName());
				newBlogger.setCountryCode(registration.getCountryCode());
				newBlogger.setCreationDate(new java.util.Date());
				return newBlogger;
			});
		}

		// 🔹 Update blogger info
		blogger.setSourceApp(sourceApp); // old field equivalent to “sourceAppType”
		blogger.setTermsAccepted(true);
		blogger.setUpdatedDate(new Date()); // legacy Date field

		// 🔹 Country & network details
		blogger.setCountryCode(registration.getCountryCode());
		try {
			if (registration.getKvsmsNetworkId() != null)
				blogger.setKvsmsNetworkId(Long.valueOf(registration.getKvsmsNetworkId()));
			if (registration.getKvsmsNodeId() != null)
				blogger.setKvsmsNodeId(Integer.valueOf(registration.getKvsmsNodeId()));
		} catch (NumberFormatException e) {
			log.warn("⚠️ Invalid kvsms ID(s) in registration: {}", e.getMessage());
		}

		// 🔹 Set contact info
		if ("p".equalsIgnoreCase(contactType)) {
			blogger.setPrimaryNumber(contactId);
		} else {
			blogger.setEmail(contactId);
		}

		// 🔹 Set signup date if not set already
		if (blogger.getIvSignupDate() == null)
			blogger.setIvSignupDate(new Date());

		// 🔹 Save blogger and return
		Blogger saved = bloggerRepository.save(blogger);
		log.info("✅ Blogger {} (id={}) for contactId={}", (blogger.getBloggerId() == null ? "created" : "updated"),
				saved.getBloggerId(), contactId);

		return saved;
	}
}
