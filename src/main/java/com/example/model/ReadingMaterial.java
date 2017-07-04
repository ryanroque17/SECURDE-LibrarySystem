package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;


@Entity
@Table(name = "reading_material")
public class ReadingMaterial {
	@Id
	@Column(name = "reading_material_id")
	@NotEmpty(message = "*Please provide the reading material ID")
	private String readingMaterialId;
	@Column(name = "title")
	@NotEmpty(message = "*Please provide the title")
	private String title;
	@Column(name = "author")
	@NotEmpty(message = "*Please provide the author")
	private String password;
	@Column(name = "year")
	@NotEmpty(message = "*Please provide the year")
	private String year;
	@Column(name = "description")
	@NotEmpty(message = "*Please provide the description")
	private String description;
	@Column(name = "tags")
	@NotEmpty(message = "*Please provide the tags")
	private String tags;
	@Column(name = "status")
	@NotEmpty(message = "*Please provide the status")
	private String status;
	@Column(name = "publisher")
	@NotEmpty(message = "*Please provide the publisher")
	private String publisher;
	
	public String getReadingMaterialId() {
		return readingMaterialId;
	}
	public void setReadingMaterialId(String readingMaterialId) {
		this.readingMaterialId = readingMaterialId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
}
