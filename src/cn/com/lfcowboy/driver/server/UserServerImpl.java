package cn.com.lfcowboy.driver.server;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.lfcowboy.driver.dao.UserDao;
import cn.com.lfcowboy.driver.domain.User;

public class UserServerImpl implements UserServer {

	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User getUser(String userName) {
		User user = userDao.getUser(userName);
		return user;
	}

}
