package com.portale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.portale.mapper.AdminMapper;
import com.portale.model.Data;

@Service
@Repository
public class AdminService {
	@Autowired
	private AdminMapper mapper;

	public Data getUsers(int query, int page, int items_per_page) {
		Data data = new Data();
		data.setCurrent_page(page);
		data.setResults_per_page(items_per_page);
		data.setData(mapper.getUsers(query, items_per_page, page * items_per_page));
		data.setTotal_results(mapper.getUsers_Totals(query));
		return data;
	}

	public Data getUsersRoles(int page, int items_per_page) {
		Data data = new Data();
		data.setCurrent_page(page);
		data.setResults_per_page(items_per_page);
		data.setData(mapper.getUsersRoles(items_per_page, page * items_per_page));
		data.setTotal_results(mapper.getUsersRoles_Totals());
		return data;
	}
	
	
}
