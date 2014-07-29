package cn.com.lfcowboy.driver.server;

import cn.com.lfcowboy.driver.domain.User;

public interface UserServer {
	public User getUser(String userName);
}
