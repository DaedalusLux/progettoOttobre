package com.portale.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.Board;
import com.portale.model.Room;
import com.portale.security.model.AuthenticatedUser;
import com.portale.services.RoomService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("rooms")
public class RoomController {
	@Resource
	private RoomService roomService;
	
	@RequestMapping(value = "", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> getBoardsByUser(Authentication authentication) {
		AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();
		try {
			List<Board> boards = roomService.getBoardsByUser(u.getId());
			return new ResponseEntity<>(boards, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@RequestMapping(value = "/checkUsernameExistence/{username}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> checkUsernameExistence(@PathVariable("username") String username) {
	    try {
	        boolean usernameExists = roomService.checkUsernameExistence(username);
	        return new ResponseEntity<>(usernameExists, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@RequestMapping(value = "/{room_id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> getRoom(Authentication authentication, @PathVariable("room_id") int room_id) {
		AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();
		try {
			Room room = roomService.getRoom(room_id, u.getId());
			return new ResponseEntity<>(room, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
