package com.mornings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mornings.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	
	Category findByName(String name);
	List<Category> findAllByOrderBySortingAsc();
}
