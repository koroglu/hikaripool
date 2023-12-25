package com.oracle.acs.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class BaseDAO {

    @Autowired
    private DataSource dataSource;
    
    @PostConstruct
    public void init(){
    	System.out.println("Post constructed BaseDAO....");
    }
    
    
    public Long carpmaToplamaWithLeak() {
    	Long l = 1L;
    	try {
			dataSource.getConnection().prepareCall("select 4 * 4 from dual ").executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return l;
    }
}
