package com.kirusa.instablogs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kirusa.instablogs.dto.APIResponse;
import com.kirusa.instablogs.service.impl.GetLanguageDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class GetLanguageDetailsController {

	private final GetLanguageDetailsServiceImpl service;

	@GetMapping("/get_language_details")
	public APIResponse getLanguageDetails() {
		return service.getLanguageDetails();
	}
}
