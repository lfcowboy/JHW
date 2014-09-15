package cn.com.lfcowboy.driver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.User;

public interface UserDao {
	public User getUser(String account);
	public List<User> getUsers(@Param("user")User user);
	public List<User> getUsersPaged(@Param("user")User user,@Param("page")Page page);
	public boolean updateUser(User user);
	public boolean addUser(User user);
	public boolean deleteUser(int id);
	public int getTotal(@Param("user")User user);
}
