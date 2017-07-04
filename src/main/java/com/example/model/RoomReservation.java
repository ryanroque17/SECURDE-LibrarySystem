package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "room_reservation")
public class RoomReservation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "room_reservation_id")
	private String roomReservationId;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "room_id")
	private String roomId;
	@Column(name = "start_date_time")
	@NotEmpty(message = "*Please provide the start date and time")
	private String startDateTime;
	@Column(name = "end_date_time")
	@NotEmpty(message = "*Please provide the end date and time")
	private String endDateTime;
	public String getRoomReservationId() {
		return roomReservationId;
	}
	public void setRoomReservationId(String roomReservationId) {
		this.roomReservationId = roomReservationId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	public String getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}
	
}
