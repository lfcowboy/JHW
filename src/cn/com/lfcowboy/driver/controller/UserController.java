package cn.com.lfcowboy.driver.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.lfcowboy.driver.domain.User;
import cn.com.lfcowboy.driver.domain.UserType;
import cn.com.lfcowboy.driver.server.UserServer;
@Controller
public class UserController {
	private UserServer userServer;
	
	private static Logger logger = LogManager.getLogger(UserController.class.getName());

	public UserServer getUserServer() {
		return userServer;
	}

	@Autowired
	public void setUserServer(UserServer userServer) {
		this.userServer = userServer;
	}
	
	@RequestMapping(value="login")  
    public String index(Model model) {  
        String name = "tester1";  
        model.addAttribute("name", name);  
        return "login";
    }
    
	@RequestMapping(value="getUserTypes" ,method=RequestMethod.GET)  	
	public @ResponseBody List<UserType> getUserTypes()  {
		List<UserType> userTypes= userServer.getUserTypes();
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
