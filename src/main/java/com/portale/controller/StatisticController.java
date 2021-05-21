package com.portale.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.StatisticDetailsObj;
import com.portale.model.Statistics_Views;
import com.portale.services.ErrorHandlerService;
import com.portale.services.StatisticsService;

@RestController
public class StatisticController {
	@Resource
	private StatisticsService statisticsService;
	@Resource
	private ErrorHandlerService errorHandlerService;
	
	@RequestMapping(value = "/statistics-management/statistics", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> GetVisitStatus(HttpServletRequest request) {
		try {
			List<Statistics_Views> statistics_Views = statisticsService.GetVisitStatus();
			return new ResponseEntity<>(statistics_Views, HttpStatus.OK);
		}
		catch(Exception e)
		{	
			errorHandlerService.submitError(500, e, null, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/statistics-management/wvs", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> WVS(HttpServletRequest request, @RequestParam int obj, @RequestParam int dett) {
		try {
			StatisticDetailsObj statistics_Views = statisticsService.GetWSV_Prod(obj, dett);
			return new ResponseEntity<>(statistics_Views, HttpStatus.OK);
		}
		catch(Exception e)
		{	
			errorHandlerService.submitError(500, e, null, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/statistics-management/wvstopst", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> WVSTS(HttpServletRequest request) {
		try {
			List<StatisticDetailsObj> statistics_Views = statisticsService.GetWSV_TopStores();
			return new ResponseEntity<>(statistics_Views, HttpStatus.OK);
		}
		catch(Exception e)
		{	
			errorHandlerService.submitError(500, e, null, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/statistics-management/wvstoppr", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> WVSTP(HttpServletRequest request) {
		try {
			List<StatisticDetailsObj> statistics_Views = statisticsService.GetWSV_TopProd();
			return new ResponseEntity<>(statistics_Views, HttpStatus.OK);
		}
		catch(Exception e)
		{	
			errorHandlerService.submitError(500, e, null, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = "/statistics-management/wvstopstr", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> WVSTST(HttpServletRequest request) {
		try {
			List<StatisticDetailsObj> statistics_Views = statisticsService.GetWSV_TopStorages();
			return new ResponseEntity<>(statistics_Views, HttpStatus.OK);
		}
		catch(Exception e)
		{	
			errorHandlerService.submitError(500, e, null, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
