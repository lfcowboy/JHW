package cn.com.lfcowboy.driver.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody
	JSONPObject login(User user) {
		User loginUser = userServer.getUser(user.getAccount());
		if (loginUser != null
				&& loginUser.getPassword().equals(user.getPassword())) {
			return new JSONPObject("myFunction", "userManagement.do");
		} else {
			return new JSONPObject("alert", "登录信息不正确，请确认后重新输入！");
		}
	}

	@RequestMapping(value = "userManagement", method = RequestMethod.GET)
	public ModelAndView loadUserManagement(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("user/userManagement");
		return mode;
	}

	@RequestMapping(value = "getUsers", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<User> getUsers(HttpServletRequest request,HttpServletResponse response) {
		Page page = new Page();
		String rangeHeader = request.getHeader("Range");
		if(rangeHeader != null && rangeHeader.matches("^items=[0-9]+-[0-9]+")){
			String[] resultRange = rangeHeader.substring(rangeHeader.lastIndexOf("=")+1).split("-");
			page.setOffset(Integer.valueOf(resultRange[0]));
			page.setLimit(Integer.valueOf(resultRange[1]));			
		}
		List<User> users = userServer.getUsers( page);
		int total  =  userServer.getTotal();
		response.setHeader("Content-Range", rangeHeader + "/" + total);
		return users;
	}

	@RequestMapping(value = "getUsers", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody boolean addUser( @RequestBody User user) {
		return userServer.addUser(user);
	}
	
	@RequestMapping(value = "getUsers/{id}", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody boolean updateUser(@PathVariable int id, @RequestBody User user) {
		return userServer.updateUser(user);
	}

	@RequestMapping(value = "getUsers/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody boolean deleteUser(@PathVariable int id) {
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
