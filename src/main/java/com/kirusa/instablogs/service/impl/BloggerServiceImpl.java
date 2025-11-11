package com.kirusa.instablogs.service.impl;

import org.springframework.stereotype.Service;

import com.kirusa.instablogs.model.Blogger;
import com.kirusa.instablogs.repository.UserDeviceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BloggerServiceImpl {

	private final UserDeviceRepository userDeviceRepository;

	public boolean isIVUser(Blogger blogger) {
		return blogger.getIvSignupDate() != null;
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
}
