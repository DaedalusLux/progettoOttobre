package com.portale.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.EmailObj;
import com.portale.model.ItemObject;
import com.portale.model.NotificationObject;
import com.portale.services.EmailService;
import com.portale.services.ErrorHandlerService;
import com.portale.services.NotificationService;
import com.portale.services.PDFService;
import com.portale.services.StoreService;
import com.portale.services.UserService;

@RestController
public class PDFController {
	@Resource
	private EmailService emailService;
	@Resource
	private NotificationService notificationService;
	@Resource
	private ErrorHandlerService errorHandlerService;
	@Resource
	private UserService userService;
	@Resource
	private StoreService storeService;
	@Resource
	private PDFService pdfService;
	
	@RequestMapping(value = "/pdf-management/userprint/{item_id}", method = RequestMethod.GET)
	public ResponseEntity<?> SendContactEmail(HttpServletRequest request, @PathVariable int item_id) {
		try {
			ItemObject productInformation = new ItemObject();
			productInformation = storeService.GetStoreProductinfo(item_id);
			String pdfOutput = pdfService.UserPrint(productInformation);
			return new ResponseEntity<>(pdfOutput, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			//errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
