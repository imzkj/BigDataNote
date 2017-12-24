package SignletonLogin;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class User implements HttpSessionBindingListener {

	private String name;
	private UserList ul = UserList.getInstance();

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void valueBound(HttpSessionBindingEvent event) {
		ul.addUser(name);
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		ul.removeUser(name);
	}

}
