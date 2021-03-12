package com.portale.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.Statistics_Views;
import com.portale.services.StatisticsService;

@RestController
public class StatisticController {
	@Resource
	private StatisticsService statisticsService;
	
	@RequestMapping(value = "/statistics-management/statistics", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> GetVisitStatus() {
		try {
			List<Statistics_Views> statistics_Views = statisticsService.GetVisitStatus();
			return new ResponseEntity<>(statistics_Views, HttpStatus.OK);
		}
		catch(Exception e)
		{	
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
