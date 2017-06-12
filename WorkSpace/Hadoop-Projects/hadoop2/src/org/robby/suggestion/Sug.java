package org.robby.suggestion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.opensymphony.xwork2.ActionSupport;

public class Sug extends ActionSupport{
	String query;
	Set<String> result;
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	} 
	
	public Set<String> getResult() {
		System.out.println(query);
		
		Jedis jedis = new Jedis("192.168.1.121");
		result = jedis.zrevrange(query, 0, 5);
		
		return result;
	}

	public void setResult(Set<String> result) {
		this.result = result;
	}
	
	public String execute() throws Exception {
		return SUCCESS;
	}
}
