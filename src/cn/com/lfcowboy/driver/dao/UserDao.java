package cn.com.lfcowboy.driver.dao;

import java.util.List;

import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.User;

public interface UserDao {
	User getUser(String name);
	List<User> getUsers(Page page);
	boolean updateUser(User user);
	boolean addUser(User user);
	boolean deleteUser(int id);
	int getTotal();
}
