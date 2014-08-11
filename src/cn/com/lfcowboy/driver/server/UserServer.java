package cn.com.lfcowboy.driver.server;

import java.util.List;

import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.User;
import cn.com.lfcowboy.driver.domain.UserType;

public interface UserServer {
	public User getUser(String userName);
	public List<User> getUsers(Page page);
	public List<UserType> getUserTypes();
	public boolean updateUser(User user);
	public boolean addUser(User user);
	public boolean deleteUser(int id);
	public  int getTotal();
}
