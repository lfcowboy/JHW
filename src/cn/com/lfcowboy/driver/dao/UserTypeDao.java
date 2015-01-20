package cn.com.lfcowboy.driver.dao;

import java.util.List;

import cn.com.lfcowboy.driver.domain.UserType;

public interface UserTypeDao {
	List<UserType> getUserTypes(String operator);
}
