package SignletonLogin;

import java.util.Enumeration;
import java.util.Vector;

public class UserList {
	private static final UserList userList = new UserList();
	private Vector<String> v;

	private UserList() {
		v = new Vector<String>();
	}

	public static UserList getInstance() {
		return userList;
	}

	public void addUser(String name) {
		if (name != null) {
			v.addElement(name);
		}
	}

	public void removeUser(String name) {
		if (name != null) {
			v.remove(name);
		}
	}

	public Enumeration<String> getUserList() {
		return v.elements();
	}

	public int getUserCount() {
		return v.size();
	}

}
