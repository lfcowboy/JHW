package cn.com.lfcowboy.driver.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.com.lfcowboy.driver.domain.Driver;
import cn.com.lfcowboy.driver.domain.JSONObject;
import cn.com.lfcowboy.driver.domain.JSONResult;
import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.Version;
import cn.com.lfcowboy.driver.server.DriverServer;
import cn.com.lfcowboy.driver.server.VersionServer;

@Controller
public class DriverController {
	private DriverServer driverServer;
	private VersionServer versionServer;
	
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
	public ModelAndView LoadEditUserDialog(HttpServletRequest request,
			HttpServletResponse response,String name) throws Exception {
		ModelAndView mode = new ModelAndView("driver/EditDriverDialog");
		Driver driver = driverServer.getDriver(name);  
		mode.addObject("driver", driver);
		return mode;
	}
	
	@RequestMapping(value = "loadVersionManagement", method = RequestMethod.GET)
	public ModelAndView loadVersionManagement(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("driver/versionManagement");
		return mode;
	}
	
	@RequestMapping(value = "LoadAddVersionDialog", method = RequestMethod.GET)
	public ModelAndView LoadAddVersionDialog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("driver/AddVersionDialog");
		return mode;
	}
	
	@RequestMapping(value = "getDriversAction", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Driver> getDriversAction(HttpServletRequest request,
			HttpServletResponse response, Driver driver) throws Exception{
		Page page = new Page();
		String rangeHeader = request.getHeader("Range");
		if (rangeHeader != null && rangeHeader.matches("^items=[0-9]+-[0-9]+")) {
			String[] resultRange = rangeHeader.substring(
					rangeHeader.lastIndexOf("=") + 1).split("-");
			page.setOffset(Integer.valueOf(resultRange[0]));
			page.setLimit(Integer.valueOf(resultRange[1]) - page.getOffset()
					+ 1);
		}
		List<Driver> drivers = driverServer.getDrivers(driver, page);
		int total = driverServer.getTotal(driver);
		response.setHeader("Content-Range", rangeHeader + "/" + total);
		return drivers;
	}
	
	@RequestMapping(value = "getVersionsAction", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Version> getVersionsAction(HttpServletRequest request,
			HttpServletResponse response, int driverId) throws Exception{
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
	public @ResponseBody JSONResult addDriverAction(Driver driver) {
		JSONResult result = new JSONResult();
		Driver driverExist = driverServer.getDriver(driver.getName());
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
	public @ResponseBody JSONResult editDriverAction(Driver driver) {
		JSONResult result = new JSONResult();
		Driver editUser = driverServer.getDriver(driver.getName());
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
	boolean ProductsAction(@PathVariable int id) {
		return driverServer.deleteDriver(id);
	}
	
    @RequestMapping(value="uploadDriverFileAction", method=RequestMethod.POST)  
    public @ResponseBody Map<String, Object> addUser(Version version, @RequestParam MultipartFile myfile, HttpServletRequest request) throws IOException{    
		JSONObject result = new JSONObject();
		if (myfile.isEmpty()) {
			System.out.println("文件未上传");
		} else {
			System.out.println("文件长度: " + myfile.getSize());
			System.out.println("文件类型: " + myfile.getContentType());
			System.out.println("文件名称: " + myfile.getName());
			System.out.println("文件原名: " + myfile.getOriginalFilename());
			System.out.println("========================================");
			// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
			String realPath = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/upload");
			// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
			FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(
					realPath, myfile.getOriginalFilename()));
		}
		Map<String, Object> json = result.getJsonResult();
		json.put("name", "test.txt");
		json.put("file", myfile.getName());
		json.put("type", "txt");
		return json;
    }
    
	@RequestMapping(value = "addVersionAction", method = RequestMethod.POST)
	public @ResponseBody JSONResult addVersionAction(Version version,@RequestParam MultipartFile myfile,HttpServletRequest request) throws IOException{
		JSONResult result = new JSONResult();
		if (myfile.isEmpty()) {
			System.out.println("文件未上传");
		} else {
			System.out.println("文件长度: " + myfile.getSize());
			System.out.println("文件类型: " + myfile.getContentType());
			System.out.println("文件名称: " + myfile.getName());
			System.out.println("文件原名: " + myfile.getOriginalFilename());
			System.out.println("========================================");
			// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
			String realPath = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/upload");
			// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
			FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(
					realPath, myfile.getOriginalFilename()));
		}
//		Version versionExist = versionServer.getVersions(version.getDriverId());
//		if (versionExist != null) {
//			result.setSuccess(false);
//			result.setMsg("版本已存在，请使用其他程序名！");			
//		} else {
//			versionServer.addVersion(version);
			result.setSuccess(true);
//		}
		return result;
	}
}
