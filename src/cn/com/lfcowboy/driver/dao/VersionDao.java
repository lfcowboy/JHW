package cn.com.lfcowboy.driver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.Version;

public interface VersionDao {
	Version getVersion(int id);

	Version getVersionByCustomer(@Param("versionId") int versionId,
			@Param("customerId") int customerId);

	List<Version> getVersions(int driverId);

	List<Version> getTestVersions(@Param("driverId") int driverId,
			@Param("officialVersion") String officialVersion);

	void deleteTestVersions(@Param("driverId") int driverId,
			@Param("officialVersion") String officialVersion);

	List<Version> getVersionsPaged(@Param("driverId") int driverId,
			@Param("page") Page page);

	boolean updateVersion(Version version);

	boolean addVersion(Version version);

	boolean deleteVersion(int id);

	int getTotal(@Param("driverId") int driverId);

	String getMaxVersion(@Param("driverId") int driverId);

	boolean deleteVersionByDriverId(int driverId);

}
