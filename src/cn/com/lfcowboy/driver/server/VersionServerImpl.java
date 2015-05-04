package cn.com.lfcowboy.driver.server;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.lfcowboy.driver.dao.VersionDao;
import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.Version;

public class VersionServerImpl implements VersionServer {
	private VersionDao versionDao;

	public VersionDao getVersionDao() {
		return versionDao;
	}

	@Autowired
	public void setVersionDao(VersionDao versionDao) {
		this.versionDao = versionDao;
	}

	@Override
	public Version getVersion(int id) {
		return versionDao.getVersion(id);
	}

	@Override
	public Version getVersionByCustomer(@Param("versionId") int versionId,
			@Param("customerId") int customerId){
		return versionDao.getVersionByCustomer(versionId, customerId);
	}

	@Override
	public List<Version> getVersions(int driverId) {
		return versionDao.getVersions(driverId);
	}

	@Override
	public List<Version> getTestVersions(@Param("driverId") int driverId,
			@Param("officialVersion") String officialVersion) {
		return versionDao.getTestVersions(driverId, officialVersion);
	}

	@Override
	public void deleteTestVersions(int driverId, String officialVersion) {
		versionDao.deleteTestVersions(driverId, officialVersion);
	}

	@Override
	public List<Version> getVersionsPaged(int driverId, Page page) {
		return versionDao.getVersionsPaged(driverId, page);
	}

	@Override
	public boolean updateVersion(Version version) {
		return versionDao.updateVersion(version);
	}

	@Override
	public boolean addVersion(Version version) {
		return versionDao.addVersion(version);
	}

	@Override
	public boolean deleteVersion(int id) {
		return versionDao.deleteVersion(id);
	}

	@Override
	public int getTotal(int driverId) {
		return versionDao.getTotal(driverId);
	}

	@Override
	public String getMaxVersion(int driverId) {
		return versionDao.getMaxVersion(driverId);
	}

	public boolean deleteVersionByDriverId(int driverId) {
		return versionDao.deleteVersionByDriverId(driverId);
	}
}
