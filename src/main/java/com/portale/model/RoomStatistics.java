package com.portale.model;

import java.sql.Date;
<<<<<<< HEAD
import java.util.List;

public class RoomStatistics {
	private int room_id;
	private int room_level;
	private String board_name;
	private List<RoomUsersStatistics> room_users_statistics;
	private int total_users_count;
	private Date last_room_update;

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public int getRoom_level() {
		return room_level;
	}

	public void setRoom_level(int room_level) {
		this.room_level = room_level;
	}

	public String getBoard_name() {
		return board_name;
	}

	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}

	public Date getLast_room_update() {
		return last_room_update;
	}

	public void setLast_room_update(Date last_room_update) {
		this.last_room_update = last_room_update;
	}

	public int getTotal_users_count() {
		return total_users_count;
	}

	public void setTotal_users_count(int total_users_count) {
		this.total_users_count = total_users_count;
	}

	public List<RoomUsersStatistics> getRoom_users_statistics() {
		return room_users_statistics;
	}

	public void setRoom_users_statistics(List<RoomUsersStatistics> room_users_statistics) {
		this.room_users_statistics = room_users_statistics;
=======

public class RoomStatistics {
	private int room_id;
	private int room_level;
	private String board_name;
	private int users_count;
	private Date last_room_update;

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public int getRoom_level() {
		return room_level;
	}

	public void setRoom_level(int room_level) {
		this.room_level = room_level;
	}

	public String getBoard_name() {
		return board_name;
	}

	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}

	public int getUsers_count() {
		return users_count;
	}

	public void setUsers_count(int users_count) {
		this.users_count = users_count;
	}

	public Date getLast_room_update() {
		return last_room_update;
	}

	public void setLast_room_update(Date last_room_update) {
		this.last_room_update = last_room_update;
>>>>>>> branch 'master' of https://github.com/DaedalusLux/progettoOttobre
	}

}
