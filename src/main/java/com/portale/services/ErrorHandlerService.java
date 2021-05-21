package com.portale.services;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.portale.mapper.ErrorHandlerMapper;
import com.portale.security.model.AuthenticatedUser;

@Service
@Repository
public class ErrorHandlerService {
	@Autowired
	private ErrorHandlerMapper errorHandlerMapper;
	
	public void submitError(int statuscode, Exception e, Authentication entity, HttpServletRequest request) {
		int uid = 0;
		if(entity!=null) {
			AuthenticatedUser u = (AuthenticatedUser) entity.getPrincipal();
			uid = u.getUsr_id();
		}
		System.out.println(e.getMessage());
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String sStackTrace = sw.toString();
		errorHandlerMapper.submitError(statuscode, e.getMessage(), sStackTrace, uid, request.getRequestURI(), request.getQueryString(), request.getMethod());
	}
	
	public List<String> getErrorList(int limit, int lastResultId, String search){
		return errorHandlerMapper.getErrorList(limit, lastResultId, search);
	}
}
