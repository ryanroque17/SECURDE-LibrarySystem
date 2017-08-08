package com.example.model;

import java.util.Date;

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
	private Date reservationDate;
	@Column(name = "return_date")
	private Date returnDate;
	
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
	public Date getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
}
