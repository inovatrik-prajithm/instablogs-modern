package com.kirusa.instablogs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kirusa.instablogs.dto.APIResponse;
import com.kirusa.instablogs.service.impl.GetCategoryServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class GetCategoryInfoController {

	private final GetCategoryServiceImpl getCategoryService;

	@GetMapping("/category")
	public APIResponse getCategoryById(@RequestParam Long bloggerId) {
		return getCategoryService.getCategoryById(bloggerId);
	}
}
