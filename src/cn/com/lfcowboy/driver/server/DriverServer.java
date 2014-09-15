package cn.com.lfcowboy.driver.server;

import java.util.List;

import cn.com.lfcowboy.driver.domain.Driver;
import cn.com.lfcowboy.driver.domain.Page;

public interface DriverServer {
	public Driver getDriver(String driverCode);

	public List<Driver> getDrivers(Driver driver, Page page);

	public boolean updateDriver(Driver driver);

	public boolean addDriver(Driver driver);

	public boolean deleteDriver(int id);

	public int getTotal(Driver driver);
}
