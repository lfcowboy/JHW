package cn.com.lfcowboy.driver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.lfcowboy.driver.domain.Driver;
import cn.com.lfcowboy.driver.domain.Page;

public interface DriverDao {
	public Driver getDriver(int id);

	public List<Driver> getDrivers(@Param("driver") Driver driver,
			@Param("page") Page page);

	public boolean updateDriver(Driver driver);

	public boolean addDriver(Driver driver);

	public boolean deleteDriver(int id);

	int getTotal(@Param("driver") Driver driver);
}
