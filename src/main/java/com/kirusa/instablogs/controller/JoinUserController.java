package com.kirusa.instablogs.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kirusa.instablogs.dto.APIRequest;
import com.kirusa.instablogs.dto.APIResponse;
import com.kirusa.instablogs.service.impl.JoinUserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class JoinUserController {

	private final JoinUserServiceImpl joinUserService;

	@PostMapping("/join")
	public APIResponse joinUser(@Valid @RequestBody APIRequest request) {
		return joinUserService.handleJoinUser(request);
	}
}
