package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "room")
public class Room {
	@Id
	@Column(name = "room_id")
	@NotEmpty(message = "*Please provide the room ID")
	private String roomId;
	@Column(name = "is_available")
	@NotEmpty(message = "*Please provide the room ID")
	private String isAvailable;
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	
}
