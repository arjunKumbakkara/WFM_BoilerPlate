package com.sixdee.wfm.configuration;
/**
* @author Arjun Kumbakkara[2019@sixdee|WFM|v1.0.0] 
*/

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.crypto.KeyGenerator;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CacheActivity {
	private static final Logger logger = LogManager.getLogger(CacheActivity.class);
	 	@Autowired
	 	Globals refreshCacheActivity;
	 	boolean Refreshed=false;
	    public  boolean RefreshCache(){
	    	 ConcurrentHashMap<String, String> tokenMap = new ConcurrentHashMap<String,String>();
			 boolean isRefreshed=false;
		try{
			//refreshCacheActivity.RefreshCache("S");
			isRefreshed=true;
			Refreshed=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return isRefreshed;
	}
	
}
