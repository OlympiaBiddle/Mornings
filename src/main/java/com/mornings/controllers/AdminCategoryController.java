package com.mornings.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mornings.model.Category;
import com.mornings.model.Page;
import com.mornings.repository.CategoryRepository;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

	@Autowired
	private CategoryRepository categoryRepo;

	@GetMapping
	public String index(Model model) {

		List<Category> categories = categoryRepo.findAll();

		model.addAttribute("categories", categories);

		return "admin/categories/index";
	}

	/* Using modelAtrribute to add new object
	 * Differnet ways to do the same thing */
//	@ModelAttribute("category")
//	public Category category() {
//		return new Category();
//	}

	@GetMapping("/add")
	public String add(@ModelAttribute Category category) {

		return "admin/categories/add";
	}
	
	/* ----------Add objects to class but first checks if exist already----- */
    /* ----------or if property Slug is empty replaces it with title----- */
    @PostMapping("/add")
    public String add(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/categories/add";
        }

        redirectAttributes.addFlashAttribute("message", "You added a new category");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = category.getName().toLowerCase().replace(" ", "-");

       Category categoriesExists = categoryRepo.findByName(category.getName());

        if ( categoriesExists != null ) {
        	//changes the error msg to red to alert admin that exists
            redirectAttributes.addFlashAttribute("message", "That category already exist");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            //The values in the text field will stay on a redirect
            redirectAttributes.addFlashAttribute("Info", category);

        } else {
            category.setSlug(slug);
            category.setSorting(25);
            //Saves Page entity
            categoryRepo.save(category);
        }

        return "redirect:/admin/categories/add";
    }

}
