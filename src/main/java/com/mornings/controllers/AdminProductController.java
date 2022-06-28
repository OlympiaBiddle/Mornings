package com.mornings.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mornings.model.Product;
import com.mornings.repository.ProductRepository;

@Controller
@RequestMapping("admin/products")
public class AdminProductController {

	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping
	public String index(Model model) {
		List<Product> products = productRepo.findAll();
		
		model.addAttribute("products", products);
		
		return "admin/products/index";
	}
}
