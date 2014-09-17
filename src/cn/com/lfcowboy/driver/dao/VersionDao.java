package cn.com.lfcowboy.driver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.Version;

public interface VersionDao {
	public Version getVersion(int id);

	public List<Version> getVersions(int driverId);

	public List<Version> getVersionsPaged(@Param("driverId") int driverId,
			@Param("page") Page page);

	public boolean updateVersion(Version version);

	public boolean addVersion(Version version);

	public boolean deleteVersion(int id);

	int getTotal(@Param("driverId") int driverId);
}
