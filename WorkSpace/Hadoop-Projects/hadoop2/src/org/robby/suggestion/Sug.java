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
		//两个关键字查询，按空格分割
        String[] a = query.split(" ");
        //指定redis服务运行所在机器
		Jedis jedis = new Jedis("192.168.1.121");
		//取两个关键字相同的value合集并返回
        jedis.zinterstore("_tmpstore", a[0], a[1]);
        result = jedis.zrevrange("_tmpstore", 0, 5);
        //删除临时关键字
		jedis.del("_tmpstore");
		return result;
	}

	public void setResult(Set<String> result) {
		this.result = result;
	}
	
	public String execute() throws Exception {
		return SUCCESS;
	}
}
