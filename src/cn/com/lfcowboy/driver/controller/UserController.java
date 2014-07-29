package cn.com.lfcowboy.driver.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.lfcowboy.driver.domain.User;
import cn.com.lfcowboy.driver.server.UserServer;
@Controller
public class UserController {
	UserServer userServer;

	public UserServer getUserServer() {
		return userServer;
	}

	@Autowired
	public void setUserServer(UserServer userServer) {
		this.userServer = userServer;
	}
	
	@RequestMapping("index")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("index");
		User user = userServer.getUser("zhouyunli");
		mode.addObject("user", user);
		return mode;
	}	
}
