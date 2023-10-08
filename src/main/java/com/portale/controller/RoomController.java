package com.portale.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.Board;
import com.portale.model.Room;
import com.portale.services.RoomService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("rooms")
public class RoomController {
	@Resource
	private RoomService roomService;
	
	@RequestMapping(value = "/{room_id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> getRoom(HttpServletRequest request, @PathVariable("room_id") int room_id) {
		try {
			Room room = roomService.getRoom(room_id);
			return new ResponseEntity<>(room, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> getBoardsByUser(HttpServletRequest request, @PathVariable("user_id") int user_id) {
		try {
			List<Board> boards = roomService.getBoardsByUser(user_id);
			return new ResponseEntity<>(boards, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
