package com.Library.Mangement.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private Long id;

	@NotBlank(message = "Title can't be blank")
	@JsonProperty
	private String title;

	@NotBlank(message = "Author can't be blank")
	@JsonProperty
	private String author;

	@NotBlank(message = "ISBN can't be blank")
	@JsonProperty
	private String isbn;

	@NotNull(message = "Publication year can't be blank")
	@JsonProperty
	@Column(name = "publication_year")
	private Integer publicationYear;

	@NotBlank(message = "Description can't be blank")
	@JsonProperty
	private String description;

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getDescription() {
		
		return description;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}



	
	
}
