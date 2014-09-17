package cn.com.lfcowboy.driver.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.lfcowboy.driver.domain.JSONResult;
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
		ModelAndView mode = new ModelAndView("product/ProductManagement");
		return mode;
	}

	@RequestMapping(value = "LoadAddProductDialog", method = RequestMethod.GET)
	public ModelAndView LoadAddProductDialog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("product/AddProductDialog");
		return mode;
	}

	@RequestMapping(value = "LoadEditProductDialog", method = RequestMethod.GET)
	public ModelAndView LoadEditUserDialog(HttpServletRequest request,
			HttpServletResponse response, String code) throws Exception {
		ModelAndView mode = new ModelAndView("product/EditProductDialog");
		Product product = productServer.getProduct(code);
		mode.addObject("product", product);
		return mode;
	}

	@RequestMapping(value = "loadProductDriverManagement", method = RequestMethod.GET)
	public ModelAndView loadProductDriverManagement(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mode = new ModelAndView("product/ProductDriverManagement");
		return mode;
	}

	@RequestMapping(value = "getProductsPagedAction", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Product> getProductsPagedAction(HttpServletRequest request,
			HttpServletResponse response, Product product) throws Exception {
		Page page = new Page();
		String rangeHeader = request.getHeader("Range");
		if (rangeHeader != null && rangeHeader.matches("^items=[0-9]+-[0-9]+")) {
			String[] resultRange = rangeHeader.substring(
					rangeHeader.lastIndexOf("=") + 1).split("-");
			page.setOffset(Integer.valueOf(resultRange[0]));
			page.setLimit(Integer.valueOf(resultRange[1]) - page.getOffset()
					+ 1);
		}
		List<Product> products = productServer.getProductsPaged(product, page);
		int total = productServer.getTotal(product);
		response.setHeader("Content-Range", rangeHeader + "/" + total);
		return products;
	}

	@RequestMapping(value = "getProductDriverPagedAction", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Product> getProductDriverPagedAction(HttpServletRequest request,
			HttpServletResponse response, Product product) throws Exception {
		Page page = new Page();
		String rangeHeader = request.getHeader("Range");
		if (rangeHeader != null && rangeHeader.matches("^items=[0-9]+-[0-9]+")) {
			String[] resultRange = rangeHeader.substring(
					rangeHeader.lastIndexOf("=") + 1).split("-");
			page.setOffset(Integer.valueOf(resultRange[0]));
			page.setLimit(Integer.valueOf(resultRange[1]) - page.getOffset()
					+ 1);
		}
		List<Product> products = productServer.getProductDriverPaged(product,
				page);
		int total = productServer.getProductDriverPagedTotal(product);
		response.setHeader("Content-Range", rangeHeader + "/" + total);
		return products;
	}

	@RequestMapping(value = "getProductsAction", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Product> getProductsAction(HttpServletRequest request,
			HttpServletResponse response, Product product) throws Exception {
		List<Product> products = productServer.getProducts(product);
		return products;
	}

	@RequestMapping(value = "addProductAction", method = RequestMethod.POST)
	public @ResponseBody
	JSONResult addProductAction(Product product) {
		JSONResult result = new JSONResult();
		Product productExist = productServer.getProduct(product.getCode());
		if (productExist != null) {
			result.setSuccess(false);
			result.setMsg("产品型号已存在，请使用其他产品型号！");
		} else {
			productServer.addProduct(product);
			result.setSuccess(true);
		}
		return result;
	}

	@RequestMapping(value = "editProductAction", method = RequestMethod.POST)
	public @ResponseBody
	JSONResult editProductAction(Product product) {
		JSONResult result = new JSONResult();
		Product editUser = productServer.getProduct(product.getCode());
		if (editUser == null) {
			result.setSuccess(false);
			result.setMsg("该产品不存在，请重试查找！");
		} else {
			productServer.updateProduct(product);
			result.setSuccess(true);
		}
		return result;
	}

	@RequestMapping(value = "getProductsAction/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	boolean ProductsAction(@PathVariable int id) {
		return productServer.deleteProduct(id);
	}
}
