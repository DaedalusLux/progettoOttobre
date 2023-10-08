package com.portale.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.portale.mapper.RoomMapper;
import com.portale.model.Board;
import com.portale.model.Room;

@Service
@Repository
public class RoomService {
	@Autowired
	private RoomMapper mapper;

	public Room getRoom(int room_id) {
		return mapper.getRoom(room_id);
	}

	public List<Board> getBoardsByUser(int user_id) {
		return mapper.getBoardsByUser(user_id);
	}

}
