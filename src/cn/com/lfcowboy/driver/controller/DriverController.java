package cn.com.lfcowboy.driver.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.com.lfcowboy.driver.domain.Driver;
import cn.com.lfcowboy.driver.domain.JSONResult;
import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.User;
import cn.com.lfcowboy.driver.domain.Version;
import cn.com.lfcowboy.driver.server.DriverServer;
import cn.com.lfcowboy.driver.server.UserServer;
import cn.com.lfcowboy.driver.server.VersionServer;

@Controller
public class DriverController {
	public static final String FILF_NAME_SUFFIX = ".hex";
	public static final String UPLOAD_FILE_PATH = "D:/upload";
	private DriverServer driverServer;
	private VersionServer versionServer;
	private UserServer userServer;

	public UserServer getUserServer() {
		return userServer;
	}

	@Autowired
	public void setUserServer(UserServer userServer) {
		this.userServer = userServer;
	}

	public VersionServer getVersionServer() {
		return versionServer;
	}

	@Autowired
	public void setVersionServer(VersionServer versionServer) {
		this.versionServer = versionServer;
	}

	public DriverServer getDriverServer() {
		return driverServer;
	}

	@Autowired
	public void setDriverServer(DriverServer driverServer) {
		this.driverServer = driverServer;
	}

	@RequestMapping(value = "loadDriverManagement", method = RequestMethod.GET)
	public ModelAndView loadDriverManagement(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("driver/driverManagement");
		return mode;
	}

	@RequestMapping(value = "LoadAddDriverDialog", method = RequestMethod.GET)
	public ModelAndView LoadAddDriverDialog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("driver/AddDriverDialog");
		return mode;
	}

	@RequestMapping(value = "LoadEditDriverDialog", method = RequestMethod.GET)
	public ModelAndView LoadEditDriverDialog(HttpServletRequest request,
			HttpServletResponse response, int id) throws Exception {
		ModelAndView mode = new ModelAndView("driver/EditDriverDialog");
		Driver driver = driverServer.getDriver(id);
		mode.addObject("driver", driver);
		return mode;
	}

	@RequestMapping(value = "loadVersionManagement", method = RequestMethod.GET)
	public ModelAndView loadVersionManagement(HttpServletRequest request,
			HttpServletResponse response, String driverId) throws Exception {
		ModelAndView mode = new ModelAndView("driver/versionManagement");
		Driver driver = driverServer.getDriver(Integer.valueOf(driverId));
		mode.addObject("driver", driver);
		mode.addObject("driverId", driverId);
		return mode;
	}

	@RequestMapping(value = "LoadAddVersionDialog", method = RequestMethod.GET)
	public ModelAndView LoadAddVersionDialog(HttpServletRequest request,
			HttpServletResponse response, String driverId) throws Exception {
		ModelAndView mode = new ModelAndView("driver/AddVersionDialog");
		String lastVersion = versionServer.getMaxVersion(Integer
				.valueOf(driverId));
		float tmp = 0f;
		if (lastVersion != null) {
			tmp = Float.parseFloat(lastVersion);
			if (lastVersion.length() == 5) {
				tmp = 0.001f + tmp;
			} else {
				tmp = 0.1f + tmp;
			}
		}
		lastVersion = String.format("%.3f", tmp);
		mode.addObject("lastVersion", lastVersion);
		mode.addObject("driverId", driverId);
		return mode;
	}

	@RequestMapping(value = "LoadEditVersionDialog", method = RequestMethod.GET)
	public ModelAndView LoadEditVersionDialog(HttpServletRequest request,
			HttpServletResponse response, int versionId) throws Exception {
		ModelAndView mode = new ModelAndView("driver/EditVersionDialog");
		Version version = versionServer.getVersion(versionId);
		mode.addObject("version", version);
		return mode;
	}

	@RequestMapping(value = "getDriversAction", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Driver> getDriversAction(HttpServletRequest request,
			HttpServletResponse response, Driver driver) throws Exception {
		Page page = new Page();
		String rangeHeader = request.getHeader("Range");
		if (rangeHeader != null && rangeHeader.matches("^items=[0-9]+-[0-9]+")) {
			String[] resultRange = rangeHeader.substring(
					rangeHeader.lastIndexOf("=") + 1).split("-");
			page.setOffset(Integer.valueOf(resultRange[0]));
			page.setLimit(Integer.valueOf(resultRange[1]) - page.getOffset()
					+ 1);
		}
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		if ("UserType_6".equals(loginUser.getType())) {
			driver.setCustomerId(loginUser.getId());
		}
		if ("UserType_4".equals(loginUser.getType())) {
			driver.setEngineerId(loginUser.getId());
		}
		List<Driver> drivers = driverServer.getDrivers(driver, page);
		int total = driverServer.getTotal(driver);
		response.setHeader("Content-Range", rangeHeader + "/" + total);
		return drivers;
	}

	@RequestMapping(value = "getVersionsAction", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Version> getVersionsAction(HttpServletRequest request,
			HttpServletResponse response, int driverId) throws Exception {
		Page page = new Page();
		String rangeHeader = request.getHeader("Range");
		if (rangeHeader != null && rangeHeader.matches("^items=[0-9]+-[0-9]+")) {
			String[] resultRange = rangeHeader.substring(
					rangeHeader.lastIndexOf("=") + 1).split("-");
			page.setOffset(Integer.valueOf(resultRange[0]));
			page.setLimit(Integer.valueOf(resultRange[1]) - page.getOffset()
					+ 1);
		}
		List<Version> version = versionServer.getVersionsPaged(driverId, page);
		int total = versionServer.getTotal(driverId);
		response.setHeader("Content-Range", rangeHeader + "/" + total);
		return version;
	}

	@RequestMapping(value = "addDriverAction", method = RequestMethod.POST)
	public @ResponseBody
	JSONResult addDriverAction(Driver driver) {
		JSONResult result = new JSONResult();
		Driver driverExist = driverServer.getDriver(driver.getId());
		if (driverExist != null) {
			result.setSuccess(false);
			result.setMsg("程序名已存在，请使用其他程序名！");
		} else {
			driverServer.addDriver(driver);
			result.setSuccess(true);
		}
		return result;
	}

	@RequestMapping(value = "editDriverAction", method = RequestMethod.POST)
	public @ResponseBody
	JSONResult editDriverAction(Driver driver) {
		JSONResult result = new JSONResult();
		Driver editUser = driverServer.getDriver(driver.getId());
		if (editUser == null) {
			result.setSuccess(false);
			result.setMsg("该程序不存在，请重试查找！");
		} else {
			driverServer.updateDriver(driver);
			result.setSuccess(true);
		}
		return result;
	}

	@RequestMapping(value = "getDriversAction/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	boolean deleteDriverAction(@PathVariable int id) {
		if (driverServer.deleteDriver(id)) {
			List<Version> versions = versionServer.getVersions(id);
			for (Version version : versions) {
				String filePath = getVersionFilePath(version);
				File dirverFile = new File(filePath);
				if (dirverFile.exists()) {
					dirverFile.delete();
				}
				versionServer.deleteVersionByDriverId(id);
			}
		}
		return true;
	}

	@RequestMapping(value = "addVersionAction", method = RequestMethod.POST)
	public @ResponseBody
	JSONResult addVersionAction(Version version, HttpServletRequest request)
			throws IOException {
		JSONResult result = new JSONResult();
		result.setSuccess(true);
		MultipartFile driverFile = version.getDriverFile();
		if (driverFile == null) {
			result.setMsg("文件上传失败!");
			result.setSuccess(false);
			return result;
		} else {
			if (!driverFile.getOriginalFilename().endsWith(FILF_NAME_SUFFIX)
					|| !"application/octet-stream".equals(driverFile
							.getContentType())) {
				result.setMsg("请选择正确的文件类型.hex!");
				result.setSuccess(false);
				return result;
			}
			version.setFileName(getVersionFileName(version));
			FileUtils.copyInputStreamToFile(version.getDriverFile()
					.getInputStream(), new File(getVersionFilePath(version)));

		}
		String officialVersion = getOfficialVersion(version.getVersion());
		List<Version> testVersions = versionServer.getTestVersions(
				version.getDriverId(), officialVersion);
		for (Version testVersion : testVersions) {
			File testVersionFile = getVersionFile(testVersion);
			if (testVersionFile.exists()) {
				testVersionFile.delete();
			}
		}
		versionServer.deleteTestVersions(version.getDriverId(),
				getOfficialVersion(version.getVersion()));
		versionServer.addVersion(version);
		return result;
	}

	private String getOfficialVersion(String testVersion) {
		return testVersion.substring(0, 3);
	}

	private File getVersionFile(Version version) {
		return new File(getVersionFilePath(version));
	}

	private String getVersionFilePath(Version version) {
		String fileNameString = getVersionFileName(version);
		if (fileNameString != null) {
			return UPLOAD_FILE_PATH + "/" + version.getDriverId() + "/"
					+ getVersionFileName(version);
		}
		return null;
	}

	private String getVersionFileName(Version version) {
		if (version.getFileName() != null) {
			return version.getFileName();
		}
		if (version.getVersion() != null) {
			return "D" + version.getDriverId() + "V" + version.getVersion()
				+ FILF_NAME_SUFFIX;
		}
		return null;
	}

	@RequestMapping(value = "editVersionAction", method = RequestMethod.POST)
	public @ResponseBody
	JSONResult editVersionAction(Version version) {
		JSONResult result = new JSONResult();
		Version editVersion = versionServer.getVersion(version.getId());
		if (editVersion == null) {
			result.setSuccess(false);
			result.setMsg("该版本不存在，请重试查找！");
		} else {
			versionServer.updateVersion(version);
			result.setSuccess(true);
		}
		return result;
	}

	@RequestMapping(value = "downloadFile", method = RequestMethod.POST)
	@ResponseBody
	public JSONResult downloadFile(int versionId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONResult result = new JSONResult();
		Version version = versionServer.getVersion(versionId);
		String contentType = "text/html;charset=UTF-8";
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String downLoadPath = getVersionFilePath(version);
		long fileLength = new File(downLoadPath).length();
		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(getVersionFileName(version).getBytes("UTF-8"),
						"UTF-8"));
		response.setHeader("Content-Length", String.valueOf(fileLength));
		File dirverFile = new File(downLoadPath);
		if (!dirverFile.exists()) {
			result.setSuccess(false);
			result.setMsg("程序文件不存在，请联系系统管理员！");
			return result;
		}
		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
		return result;
	}

	@RequestMapping(value = "burnFile", method = RequestMethod.GET)
	@ResponseBody
	public JSONResult burnFile(String userAccount, String password,
			int versionId, int burnSum, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		JSONResult result = new JSONResult();
		User loginUser = userServer.getUser(userAccount);
		if (loginUser == null || !loginUser.getPassword().equals(password)) {
			result.setSuccess(false);
			result.setMsg("用户名密码不正确，请确认后重新输入！");
			return result;
		}

		Version version = versionServer.getVersionByCustomer(versionId,
				loginUser.getId());
		if (version == null) {
			result.setSuccess(false);
			result.setMsg("版本信息错误！");
			return result;
		} else if (version.getBurnSum() < burnSum) {
			result.setSuccess(false);
			result.setMsg("烧录次数超过上限！");
			return result;
		}

		version.setBurnSum(version.getBurnSum() - burnSum);
		versionServer.updateVersion(version);

		String contentType = "text/html;charset=UTF-8";
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String downLoadPath = getVersionFilePath(version);
		File dirverFile = new File(downLoadPath);
		if (!dirverFile.exists()) {
			result.setSuccess(false);
			result.setMsg("文件不存在！");
			return result;
		}
		long fileLength = dirverFile.length();

		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(getVersionFileName(version).getBytes("UTF-8"),
						"UTF-8"));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
		return result;
	}

	@RequestMapping(value = "getVersionsAction/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	boolean deleteVersionAction(@PathVariable int id) {
		Version version = versionServer.getVersion(id);
		String filePath = getVersionFilePath(version);
		File dirverFile = new File(filePath);
		if (dirverFile.exists()) {
			dirverFile.delete();
		}
		return versionServer.deleteVersion(id);
	}

	@RequestMapping(value = "userConfirmAction", method = RequestMethod.POST)
	public @ResponseBody
	JSONResult userConfirmAction(int versionId) throws IOException {
		JSONResult result = new JSONResult();
		Version version = versionServer.getVersion(versionId);
		String testVersion = version.getVersion();
		if (testVersion.length() == 5) {
			String oldFilePath = getVersionFilePath(version);
			File oldFile = new File(oldFilePath);
			version.setVersion(getOfficialVersion(testVersion));
			version.setFileName(null);
			String newFileName = getVersionFileName(version);
			version.setFileName(newFileName);
			version.setBurnSum(0);

			File newFile = new File(getVersionFilePath(version));
			if (newFile.exists()) {
				newFile.delete();
			}
			if (oldFile.renameTo(newFile)) {
				versionServer.updateVersion(version);
				result.setSuccess(true);
				result.setMsg("版本已确认！");
			} else {
				result.setSuccess(false);
				result.setMsg("客户确认失败！");
			}
		} else {
			result.setSuccess(false);
			result.setMsg("只有测试版本才能确认！");
		}
		return result;
	}

}
