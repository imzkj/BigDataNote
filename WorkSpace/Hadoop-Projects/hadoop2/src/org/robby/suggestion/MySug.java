package org.robby.suggestion;

import com.opensymphony.xwork2.ActionSupport;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZKJ on 2017/5/29 0029.
 */
public class MySug extends ActionSupport{
    String query;
    List<String> result;

    public String getQuery() {
        return query;
    }

    public void setQuery( String query ) {
        this.query = query;
    }

    public List<String> getResult() {
        result = new ArrayList<String>();
        result.add("许两逢");
        result.add("许大官人");
        result.add("逢神");
        return result;
    }

    public void setResult( List<String> result ) {
        this.result = result;
    }

    public String execute() throws Exception {
        return SUCCESS;
    }
}
