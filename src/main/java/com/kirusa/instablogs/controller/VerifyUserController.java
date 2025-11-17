package com.kirusa.instablogs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kirusa.instablogs.dto.APIRequest;
import com.kirusa.instablogs.dto.APIResponse;
import com.kirusa.instablogs.service.impl.VerifyUserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class VerifyUserController {

	private final VerifyUserServiceImpl verifyUserService;

	@PostMapping("/verify")
	public ResponseEntity<APIResponse> verifyUser(@Valid @RequestBody APIRequest request) {
		var response = verifyUserService.handleVerifyUser(request);
		return ResponseEntity.ok(response);
	}
}
