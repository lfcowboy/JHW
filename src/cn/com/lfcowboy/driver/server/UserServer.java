package cn.com.lfcowboy.driver.server;

import java.util.List;

import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.User;
import cn.com.lfcowboy.driver.domain.UserType;

public interface UserServer {
	public User getUser(String account);

	public List<User> getUsers(User user);
	
	public List<User> getUsersPaged(User user, Page page);

	public List<UserType> getUserTypes(String operator);

	public boolean updateUser(User user);

	public boolean addUser(User user);

	public boolean deleteUser(int id);

	public int getTotal(User user);
}
