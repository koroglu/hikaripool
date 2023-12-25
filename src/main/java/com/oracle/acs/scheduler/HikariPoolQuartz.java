package com.oracle.acs.scheduler;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

@Component
public class HikariPoolQuartz {

	@Autowired 
	DataSource dataSource;
	
	private HikariPoolMXBean hikariPoolMXBean;
	
	private boolean isdumpfileCreated=false;
			
	private int maxPoolSize = 0;
	
	@Scheduled(fixedRateString =  "${oracle.acs.poolsizecheck}",initialDelay = 3000 )
	public void testMaxPoolSize () {
		if (hikariPoolMXBean==null) {
			HikariDataSource ds = (HikariDataSource) dataSource;
			maxPoolSize=ds.getMaximumPoolSize();
			hikariPoolMXBean = ds.getHikariPoolMXBean();
		}
		int activeConnections=hikariPoolMXBean.getActiveConnections();
		int totalConnections=hikariPoolMXBean.getTotalConnections();
		System.out.println("Active Connections=" + activeConnections);
		System.out.println("Total Connections = " +totalConnections );
		if (totalConnections==maxPoolSize && isdumpfileCreated==false ) {
			System.out.println("############ Generating Heap Dump as poolsize is saturated...");
			isdumpfileCreated=true;
		}
		
	}
	
	public void generateHeapDump () {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-c", "jcmd 1 GC.heap_dump /middleware/logs/hprof/hikari_pool_exceeded.hprof");
		try {
			processBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
