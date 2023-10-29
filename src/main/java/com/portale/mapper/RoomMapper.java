package com.portale.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portale.model.Board;
import com.portale.model.Room;

public interface RoomMapper {

	Room getRoom(@Param("room_id") int room_id, @Param("user_id") int user_id);

	List<Board> getBoardsByUser(@Param("user_id") int user_id);

	void setUserToRandomAvaibleGifterRoom(@Param("user_id") int user_id);
<<<<<<< HEAD
	
=======

	void setEndUserPaymentSuccess(@Param("room_id") int room_id, @Param("end_user_id") int end_user_id,
			@Param("request_user_id") int request_user_id);
>>>>>>> branch 'master' of https://github.com/DaedalusLux/progettoOttobre

}