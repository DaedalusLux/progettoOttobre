package com.portale.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.Data;
import com.portale.model.ManagedException;
import com.portale.services.AdminService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("admin")
public class AdminController {

	@Resource
	private AdminService adminService;

	@RequestMapping(value = "/get/users/{query}/{page}/{items_per_page}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> getUsers(@PathVariable("query") int query, @PathVariable("page") int page, @PathVariable("items_per_page") int items_per_page) {
		try {
			Data users = adminService.getUsers(query, page, items_per_page);
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (ManagedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/get/users/roles/{page}/{items_per_page}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> getUsersRoles(@PathVariable("page") int page, @PathVariable("items_per_page") int items_per_page) {
		try {
			Data users = adminService.getUsersRoles(page, items_per_page);
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (ManagedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	

}