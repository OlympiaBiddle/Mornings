package com.mornings.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mornings.model.Category;
import com.mornings.repository.CategoryRepository;


@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

	@Autowired
	private CategoryRepository catRepo;
	
	@GetMapping
	public String index(Model model) {
		List<Category> categories = catRepo.findAll();
		
		model.addAttribute("categories", categories);
		
		return "admin/categories/index";
	}
}