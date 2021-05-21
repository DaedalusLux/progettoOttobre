package com.portale.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portale.security.model.AuthenticatedUser;
import com.portale.services.ErrorHandlerService;

@RestController
public class ErrorHandlerController {
	@Resource
	private ErrorHandlerService errorHandlerService;
	
	@RequestMapping(value = "/management/errorhandler", method = RequestMethod.GET)
	public ResponseEntity<?> GetErrorList(
			@RequestParam(value = "per_page", defaultValue = "20", required = false) int limit,
			@RequestParam(value = "lastResultID", defaultValue = "-1", required = false) int lastResultID,
			@RequestParam(value = "search", required = false) String search) {
		try {

			List<String> errorList = errorHandlerService.getErrorList(limit, lastResultID, search);
			return new ResponseEntity<>(errorList, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
