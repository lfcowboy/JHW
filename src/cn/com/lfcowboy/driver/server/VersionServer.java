package cn.com.lfcowboy.driver.server;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.Version;

public interface VersionServer {
	public Version getVersion(int id);

	Version getVersionByCustomer(@Param("versionId") int versionId,
			@Param("customerId") int customerId);

	public List<Version> getVersions(int driverId);

	public void deleteTestVersions(@Param("driverId") int driverId,
			@Param("officialVersion") String officialVersion);

	public List<Version> getVersionsPaged(int driverId, Page page);

	public boolean updateVersion(Version version);

	public boolean addVersion(Version version);

	public boolean deleteVersion(int id);

	public int getTotal(int driverId);

	public String getMaxVersion(int driverId);

	public boolean deleteVersionByDriverId(int driverId);
}
