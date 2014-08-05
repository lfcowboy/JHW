package cn.com.lfcowboy.driver.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.lfcowboy.driver.dao.UserDao;
import cn.com.lfcowboy.driver.dao.UserTypeDao;
import cn.com.lfcowboy.driver.domain.User;
import cn.com.lfcowboy.driver.domain.UserType;

public class UserServerImpl implements UserServer {

	private UserDao userDao;
	private UserTypeDao userTypeDao;
	
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

	@Override
	public List<UserType> getUserTypes() {
		List<UserType> userTypes = userTypeDao.getUserTypes();
		return userTypes;
	}

	public UserTypeDao getUserTypeDao() {
		return userTypeDao;
	}
	
	@Autowired
	public void setUserTypeDao(UserTypeDao userTypeDao) {
		this.userTypeDao = userTypeDao;
	}
}
