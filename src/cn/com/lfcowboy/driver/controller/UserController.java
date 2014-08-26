package cn.com.lfcowboy.driver.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.lfcowboy.driver.domain.JSONResult;
import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.User;
import cn.com.lfcowboy.driver.domain.UserType;
import cn.com.lfcowboy.driver.server.UserServer;

@Controller
public class UserController {
	private UserServer userServer;

	private static Logger logger = LogManager.getLogger(UserController.class
			.getName());

	public UserServer getUserServer() {
		return userServer;
	}

	@Autowired
	public void setUserServer(UserServer userServer) {
		this.userServer = userServer;
	}

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView index() throws Exception {
		ModelAndView mode = new ModelAndView("index");
		return mode;
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody
	JSONResult login(User user) {
		JSONResult result = new JSONResult();
		User loginUser = userServer.getUser(user.getAccount());
		if (loginUser != null
				&& loginUser.getPassword().equals(user.getPassword())) {
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
			result.setMsg("登录信息不正确，请确认后重新输入！");
		}
		return result;
	}

	@RequestMapping(value = "userManagement", method = RequestMethod.GET)
	public ModelAndView loadUserManagement(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("user/userManagement");
		return mode;
	}

	@RequestMapping(value = "productManagement", method = RequestMethod.GET)
	public ModelAndView loadProductManagement(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("product/productManagement");
		return mode;
	}
	
	@RequestMapping(value = "driverManagement", method = RequestMethod.GET)
	public ModelAndView loadDriverManagement(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("driver/driverManagement");
		return mode;
	}
	
	@RequestMapping(value = "addUser", method = RequestMethod.GET)
	public ModelAndView loadAddUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("user/AddUser");
		return mode;
	}

	@RequestMapping(value = "editUser", method = RequestMethod.GET)
	public ModelAndView loadEditUser( String account) throws Exception {
		ModelAndView mode = new ModelAndView("user/EditUser");
		User user = userServer.getUser(account);
		mode.addObject("user", user);
		return mode;
	}
	
	@RequestMapping(value = "getUsers", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<User> getUsers(HttpServletRequest request,
			HttpServletResponse response, User user) {
		Page page = new Page();
		String rangeHeader = request.getHeader("Range");
		if (rangeHeader != null && rangeHeader.matches("^items=[0-9]+-[0-9]+")) {
			String[] resultRange = rangeHeader.substring(
					rangeHeader.lastIndexOf("=") + 1).split("-");
			page.setOffset(Integer.valueOf(resultRange[0]));
			page.setLimit(Integer.valueOf(resultRange[1]) - page.getOffset()
					+ 1);
		}
		List<User> users = userServer.getUsers(user, page);
		int total = userServer.getTotal(user);
		response.setHeader("Content-Range", rangeHeader + "/" + total);
		return users;
	}

	@RequestMapping(value = "addUsersAction", method = RequestMethod.POST)
	public @ResponseBody JSONResult addUserAction(User user) {
		JSONResult result = new JSONResult();
		User loginUser = userServer.getUser(user.getAccount());
		if (loginUser != null) {
			result.setSuccess(false);
			result.setMsg("用户名已存在，请使用其他用户名！");			
		} else {
			userServer.addUser(user);
			result.setSuccess(true);
		}
		return result;
	}
	
	@RequestMapping(value = "editUsersAction", method = RequestMethod.POST)
	public @ResponseBody JSONResult editUserAction(User user) {
		JSONResult result = new JSONResult();
		User loginUser = userServer.getUser(user.getAccount());
		if (loginUser == null) {
			result.setSuccess(false);
			result.setMsg("该用户不存在，请重试查找！");			
		} else {
			userServer.updateUser(user);
			result.setSuccess(true);
		}
		return result;
	}
	
	@RequestMapping(value = "getUsers", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	boolean addUser(@RequestBody User user) {
		return userServer.addUser(user);
	}

	@RequestMapping(value = "getUsers/{id}", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody
	boolean updateUser(@PathVariable int id, @RequestBody User user) {
		return userServer.updateUser(user);
	}

	@RequestMapping(value = "getUsers/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	boolean deleteUser(@PathVariable int id) {
		return userServer.deleteUser(id);
	}

	@RequestMapping(value = "getUserTypes", method = RequestMethod.GET)
	public @ResponseBody
	List<UserType> getUserTypes() {
		List<UserType> userTypes = userServer.getUserTypes();
		return userTypes;
	}

	@RequestMapping("index")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("login");
		User user = userServer.getUser("zhouyunli");
		logger.debug("名字：" + user.getName());
		logger.info(user.getName());
		mode.addObject("Account", user.getAccount());
		mode.addObject("user", user);
		return mode;
	}
}
