package com.oracle.acs.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.acs.dao.BaseDAO;

@RestController
public class HikariPoolLeak {

	@Autowired BaseDAO baseDAO;
	
	@GetMapping("/leak")
	public void leakMe() {
		baseDAO.carpmaToplamaWithLeak();
	}
}
