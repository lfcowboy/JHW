package cn.com.lfcowboy.driver.server;

import java.util.List;

import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.Product;

public interface ProductServer {
	public Product getProduct(String productCode);

	public List<Product> getProductsPaged(Product product, Page page);

	public List<Product> getProducts(Product product);
	
	public boolean updateProduct(Product product);

	public boolean addProduct(Product product);

	public boolean deleteProduct(int id);

	public int getTotal(Product product);
}
