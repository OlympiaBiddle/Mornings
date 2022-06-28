package com.mornings.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pages")
public class Page {
	 @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Integer id;

	    @Size(min = 2, message = "Please enter a Title")
	    private String title;

	    private String slug;

	    @Size(min= 5, message = "Please enter content")
	    private String content;

	    private int sorting;

		public Page() {
			
		}


		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getSlug() {
			return slug;
		}

		public void setSlug(String slug) {
			this.slug = slug;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getSorting() {
			return sorting;
		}

		public void setSorting(int sorting) {
			this.sorting = sorting;
		}

		
}

