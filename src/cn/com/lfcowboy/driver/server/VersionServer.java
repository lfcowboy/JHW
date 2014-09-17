package cn.com.lfcowboy.driver.server;

import java.util.List;

import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.Version;

public interface VersionServer {
	public Version getVersion(int id);

	public List<Version> getVersions(int driverId);

	public List<Version> getVersionsPaged(int driverId, Page page);

	public boolean updateVersion(Version version);

	public boolean addVersion(Version version);

	public boolean deleteVersion(int id);

	public int getTotal(int driverId);
}
