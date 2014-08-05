package cn.com.lfcowboy.driver.server;

import java.util.List;

import cn.com.lfcowboy.driver.domain.User;
import cn.com.lfcowboy.driver.domain.UserType;

public interface UserServer {
	public User getUser(String userName);
	public List<UserType> getUserTypes();
}
