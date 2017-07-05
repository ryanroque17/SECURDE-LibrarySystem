package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reading_material_id")
	private int readingMaterialId;
	@Column(name = "dewey_decimal")
	@NotEmpty(message = "*Please provide the Dewey Decimal classification")
	private String deweyDecimal;
	@Column(name = "title")
	@NotEmpty(message = "*Please provide the title")
	private String title;
	@Column(name = "author")
	@NotEmpty(message = "*Please provide the author")
	private String author;
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
	private String status;
	@Column(name = "publisher")
	@NotEmpty(message = "*Please provide the publisher")
	private String publisher;
	@Column(name = "type")
	private String type;
	

	public int getReadingMaterialId() {
		return readingMaterialId;
	}
	public void setReadingMaterialId(int readingMaterialId) {
		this.readingMaterialId = readingMaterialId;
	}
	public String getDeweyDecimal() {
		return deweyDecimal;
	}
	public void setDeweyDecimal(String deweyDecimal) {
		this.deweyDecimal = deweyDecimal;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
