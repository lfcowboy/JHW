package cn.com.lfcowboy.driver.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.Product;
import cn.com.lfcowboy.driver.server.ProductServer;

@Controller
public class ProductController {
	private ProductServer productServer;
	
	public ProductServer getProductServer() {
		return productServer;
	}
	
	@Autowired
	public void setProductServer(ProductServer productServer) {
		this.productServer = productServer;
	}
	
	@RequestMapping(value = "loadProductManagement", method = RequestMethod.GET)
	public ModelAndView loadProductManagement(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("product/productManagement");
		return mode;
	}
	
	@RequestMapping(value = "getProductsAction", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Product> getProductsAction(HttpServletRequest request,
			HttpServletResponse response, Product product) throws Exception{
		Page page = new Page();
		String rangeHeader = request.getHeader("Range");
		if (rangeHeader != null && rangeHeader.matches("^items=[0-9]+-[0-9]+")) {
			String[] resultRange = rangeHeader.substring(
					rangeHeader.lastIndexOf("=") + 1).split("-");
			page.setOffset(Integer.valueOf(resultRange[0]));
			page.setLimit(Integer.valueOf(resultRange[1]) - page.getOffset()
					+ 1);
		}
		List<Product> products = productServer.getProducts(product, page);
		int total = productServer.getTotal(product);
		response.setHeader("Content-Range", rangeHeader + "/" + total);
		return products;
	}
}
