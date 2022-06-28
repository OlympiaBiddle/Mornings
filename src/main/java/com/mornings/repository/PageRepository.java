package com.mornings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.mornings.model.Page;

public interface PageRepository extends JpaRepository<Page, Integer> {

	   Page findBySlug(String slug);
	   
		/* Custom query to determine if slug with an id exists
		 * however we should always look for the value of the string slug
		 * and not the id */

//	     @Query("SELECT p FROM Page p WHERE p.id != :id and p.slug = :slug")
//	     Page findBySlugAndId(int id, String slug);

	    Page findBySlugAndIdNot(String slug, int id);
 
	    List<Page> findAllByOrderBySortingAsc();
}
