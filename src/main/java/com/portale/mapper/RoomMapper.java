package com.portale.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portale.model.Board;
import com.portale.model.Room;

public interface RoomMapper {

	Room getRoom(@Param("room_id") int room_id, @Param("user_id") int user_id);

	List<Board> getBoardsByUser(@Param("user_id") int user_id);
	
	void setUserToRandomAvaibleGifterRoom(@Param("user_id") int user_id);
	
    Integer checkUsernameExistence(@Param("username") String username);

}