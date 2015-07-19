package kasyan.catalog.dao;

import kasyan.catalog.dto.User;

public interface UserDao {
	public User selectUser(String email);
	public User selectUser(int id);
	public void insertUser(User user);
}
