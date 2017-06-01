package org.robby.friend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport{
	String username;
	String watch;
	public String getWatch() {
		return watch;
	}

	public void setWatch(String watch) {
		this.watch = watch;
	}

	public String getUnwatch() {
		return unwatch;
	}

	public void setUnwatch(String unwatch) {
		this.unwatch = unwatch;
	}

	String unwatch;
	List<String> friends;
	Set<String> might;
	
	public Set<String> getMight() {
		return might;
	}

	public void setMight(Set<String> might) {
		this.might = might;
	}

	Map<String, String> peoples;
	
	public Login(){
		friends = new ArrayList<String>();
		peoples = new HashMap<String, String>();
	}
	
	public Map<String, String> getPeoples() {
		return peoples;
	}


	public void setPeoples(Map<String, String> peoples) {
		this.peoples = peoples;
	}


	public List<String> getFriends() {
		return friends;
	}


	public void setFriends(List<String> friends) {
		this.friends = friends;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String execute() throws Exception {
		Jedis jedis = new Jedis("192.168.1.121");
		String usr = jedis.hget("users", username);
		if(usr == null && username.length() > 0){
			jedis.hset("users", username, username);
		}
		
		String key = "ref_" + username;
		might = jedis.zrange(key, 0, -1);
		
		key = "fri_" + username;
		friends = jedis.lrange(key, 0, -1);
		
		
	    peoples = jedis.hgetAll("users");
	    peoples.remove(username);
	    for(String t:friends){
	    	peoples.remove(t);
	    }
		
		System.out.println(username);
		jedis.disconnect();
		return SUCCESS;
	}
	
	public String watch() throws Exception {
		System.out.println(username);
		if(watch.length() > 0){
			String key = "fri_" + username;
			Jedis jedis = new Jedis("192.168.1.121");
			jedis.lpush(key, watch);
			
			key = "ref_" + username;
			jedis.zrem(key, watch);
			jedis.disconnect();
		}
		
		
		return execute();
	}
	
	public String unwatch() throws Exception {
		System.out.println(username);
		if(unwatch.length() > 0){
			String key = "fri_" + username;
			Jedis jedis = new Jedis("192.168.1.121");
			jedis.lrem(key, 0, unwatch);
			jedis.disconnect();
		}
		
		
		return execute();
	}
}
