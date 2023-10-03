package com.portale.model;

import java.util.List;

public class Room {
	private int room_id;
	private List<UserObject> user;

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public List<UserObject> getUser() {
		return user;
	}

	public void setUser(List<UserObject> user) {
		this.user = user;
	}

}
