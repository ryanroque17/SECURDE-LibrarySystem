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
	@Column(name = "room_name")
	@NotEmpty(message = "*Please provide the room name")
	private String roomName;
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	
}
