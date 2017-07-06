package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "reading_material_reservation")
public class ReadingMaterialReservation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reservation_id")
	private String reservationId;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "reading_material_id")
	private int readingMaterialId;
	@Column(name = "reservation_date")
	@NotEmpty(message = "*Please provide the reservation date")
	private String reservationDate;
	@Column(name = "return_date")
	@NotEmpty(message = "*Please provide the return date")
	private String returnDate;
	
	public String getReservationId() {
		return reservationId;
	}
	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getReadingMaterialId() {
		return readingMaterialId;
	}
	public void setReadingMaterialId(int readingMaterialId) {
		this.readingMaterialId = readingMaterialId;
	}
	public String getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
}
