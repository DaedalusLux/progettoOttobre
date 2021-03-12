package com.portale.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.portale.mapper.StatisticsMapper;
import com.portale.model.Statistics_Views;

@Service
@Repository
public class StatisticsService {
	@Autowired
	private StatisticsMapper statisticsMapper;

	private List<Statistics_Views> memory_Statistics_Views = new ArrayList<Statistics_Views>();

	DateTimeZone timeZone = DateTimeZone.forID("Europe/Rome");
	DateTime today = DateTime.now(timeZone);

	private Statistics_Views findView(String host, List<Statistics_Views> views) {
		for (Statistics_Views statistics_View : views) {
			if (statistics_View.getIp_address().equals(host)) {
				return statistics_View;
			}
		}
		return null;
	}

	public void AddWebSiteVisit(String host) {
		try {

			// when server start today date is recorded
			// today variable is not changing
			// every time user enter "today" checks if day of month is equal to new date
			// instance
			// if "today" day of month is different server should change "today" to new date

			if (today.getDayOfMonth() != DateTime.now(timeZone).getDayOfMonth()) {

				// changing today to new date
				today = DateTime.now(timeZone);
				// every new day statistics in memory are removed
				memory_Statistics_Views = new ArrayList<Statistics_Views>();
			}

			// before adding new visit check if there already exist new visit
			Statistics_Views statistic_inMemory = findView(host, memory_Statistics_Views);

			// if visit exist
			if (statistic_inMemory != null) {

				// get the visitdate
				DateTime visitDate = new DateTime(statistic_inMemory.getDatetime(), timeZone);
				// if it was the same day do nothing

				if (visitDate.getDayOfMonth() == today.getDayOfMonth()) {

					return;

				} else {

					// if it was a different day host will be removed to add new one
					memory_Statistics_Views.remove(memory_Statistics_Views.indexOf(statistic_inMemory));
				}
			}

			Date currentdate = new Date();
			statistic_inMemory = new Statistics_Views();
			statistic_inMemory.setIp_address(host);
			statistic_inMemory.setDatetime(currentdate);
			memory_Statistics_Views.add(statistic_inMemory);
			statisticsMapper.AddWebSiteVisit(host, currentdate.getTime());

		} catch (Exception e) {
			System.out.println("AddWebSiteVisit failed: " + e);
		}

	}

	public List<Statistics_Views> GetVisitStatus() {
		return statisticsMapper.GetVisitStatus();
	}
}
