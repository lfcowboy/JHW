package cn.com.lfcowboy.driver.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.lfcowboy.driver.dao.UserDao;
import cn.com.lfcowboy.driver.dao.UserTypeDao;
import cn.com.lfcowboy.driver.domain.Page;
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
	public User getUser(String account) {
		return userDao.getUser(account);
	}

	@Override
	public List<User> getUsersPaged(User user, Page page) {
		return userDao.getUsersPaged(user, page);
	}

	@Override
	public List<User> getUsers(User user) {
		return userDao.getUsers(user);
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

	@Override
	public boolean updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public boolean addUser(User user) {
		return userDao.addUser(user);
	}

	@Override
	public boolean deleteUser(int id) {
		return userDao.deleteUser(id);
	}

	@Override
	public int getTotal(User user) {
		return userDao.getTotal(user);
	}
}
