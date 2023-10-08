package com.portale.model;

import java.util.List;

public class Room {
	private int room_id;
	private int board_level; //ID Tavola = Livello Tavola
	private String board_name; //Nome tavola
	
	private List<User> users; //Lista giocatori presenti nella tavola

	public String getBoard_name() {
		return board_name;
	}

	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public int getBoard_level() {
		return board_level;
	}

	public void setBoard_level(int board_level) {
		this.board_level = board_level;
	}
	
}
