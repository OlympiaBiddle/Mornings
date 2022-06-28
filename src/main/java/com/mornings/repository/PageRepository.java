package com.mornings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mornings.model.Page;

public interface PageRepository extends JpaRepository<Page, Integer> {

	   Page findBySlug(String slug);
	   
		/* Custom query to determine if slug with an id exists */

//	     @Query("SELECT p FROM Page p WHERE p.id != :id and p.slug = :slug")
//	     Page findBySlug(int id, String slug);

	    Page findBySlugAndIdNot(String slug, int id);

	    List<Page> findAllByOrderBySortingAsc();
}
