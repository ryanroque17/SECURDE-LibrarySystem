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
	@Column(name = "room_id")
	private String roomId;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "date")
	@NotEmpty(message = "*Please provide the date")
	private String date;
	@Column(name = "start_time")
	@NotEmpty(message = "*Please provide the start time")
	private String startTime;
	@Column(name = "end_time")
	@NotEmpty(message = "*Please provide the end time")
	private String endTime;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
