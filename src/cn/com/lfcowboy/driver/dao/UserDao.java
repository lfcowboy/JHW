package cn.com.lfcowboy.driver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.User;

public interface UserDao {
	User getUser(String account);
	List<User> getUsers(@Param("user")User user,@Param("page")Page page);
	boolean updateUser(User user);
	boolean addUser(User user);
	boolean deleteUser(int id);
	int getTotal(@Param("user")User user);
}
