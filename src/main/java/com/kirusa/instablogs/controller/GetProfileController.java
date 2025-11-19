package com.kirusa.instablogs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kirusa.instablogs.dto.APIResponse;
import com.kirusa.instablogs.service.impl.GetProfileServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class GetProfileController {

	private final GetProfileServiceImpl getProfileService;

	@GetMapping("/profile")
	public APIResponse getProfileById(@RequestParam Long bloggerId) {
		return getProfileService.getProfileById(bloggerId);
	}

}
