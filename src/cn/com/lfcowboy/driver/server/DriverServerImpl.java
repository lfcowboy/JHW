package cn.com.lfcowboy.driver.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.lfcowboy.driver.dao.DriverDao;
import cn.com.lfcowboy.driver.domain.Driver;
import cn.com.lfcowboy.driver.domain.Page;

public class DriverServerImpl implements DriverServer{
		private DriverDao driverDao;

		public DriverDao getDriverDao() {
			return driverDao;
		}

		@Autowired
		public void setDriverDao(DriverDao driverDao) {
			this.driverDao = driverDao;
		}

		@Override
		public Driver getDriver(String driverCode) {
			return driverDao.getDriver(driverCode);
		}

		@Override
		public List<Driver> getDrivers(Driver driver, Page page) {
			return driverDao.getDrivers(driver, page);
		}

		@Override
		public boolean updateDriver(Driver driver) {
			return driverDao.updateDriver(driver);
		}

		@Override
		public boolean addDriver(Driver driver) {
			return driverDao.addDriver(driver);
		}

		@Override
		public boolean deleteDriver(int id) {
			return driverDao.deleteDriver(id);
		}

		@Override
		public int getTotal(Driver driver) {
			return driverDao.getTotal(driver);
		}

}
