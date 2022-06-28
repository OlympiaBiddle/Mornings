package com.mornings.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mornings.model.Page;
import com.mornings.repository.PageRepository;

@Controller
@RequestMapping("/admin/pages")
public class AdminController {

	@Autowired
    private PageRepository pageRepo;
	
	/*-------------- Show all objects in class------------------- */

    @GetMapping
    public String index(Model model) {

        List<Page> pages = pageRepo.findAll();

        model.addAttribute("pages", pages);

        return "admin/pages/index";
    }

	/*------------------- Add objects to class---------------------- */
    @GetMapping("/add")
    public String add(@ModelAttribute Page page) {
        
        // model.addAttribute("page", new Page());

        return "admin/pages/add";
    }

	/* ----------Add objects to class but first checks if exist already----- */
    /* ----------or if property Slug is empty replaces it with title----- */
    @PostMapping("/add")
    public String add(@Valid Page page, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/pages/edit";
        }

        redirectAttributes.addFlashAttribute("message", "Page edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug().toLowerCase().replace(" ", "-");

        Page slugExists = pageRepo.findBySlug(slug);

        if ( slugExists != null ) {
        	//changes the error msg to red to alert admin that exists
            redirectAttributes.addFlashAttribute("message", "Slug exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            //The values that admin enter will stay
            redirectAttributes.addFlashAttribute("page", page);

        } else {
            page.setSlug(slug);
            page.setSorting(25);
            //Saves Page entity
            pageRepo.save(page);
        }

        return "redirect:/admin/pages/add";
    }

	/*-------------------- Edit Object------------------ */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {

        @SuppressWarnings("deprecation")
		Page page = pageRepo.getOne(id);

        model.addAttribute("page", page);

        return "admin/pages/edit";
        
    }
    
    @PostMapping("/edit")
    public String edit(@Valid Page page,
    		BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) {

        Page pageCurrent = pageRepo.getOne(page.getId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", pageCurrent.getTitle());
            return "admin/pages/edit";
        }

        redirectAttributes.addFlashAttribute("message", "Page edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug().toLowerCase().replace(" ", "-");

        Page slugExists = pageRepo.findBySlugAndIdNot(slug, page.getId());

        if ( slugExists != null ) {
            redirectAttributes.addFlashAttribute("message", "Slug exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);

        } else {
            page.setSlug(slug);

            pageRepo.save(page);
        }

        return "redirect:/admin/pages/edit/"+ page.getId();
    }
}
