package cn.com.lfcowboy.driver.dao;

import java.util.List;

import cn.com.lfcowboy.driver.domain.User;
import cn.com.lfcowboy.driver.domain.UserType;

public interface UserDao {
	User getUser(String name);
}
