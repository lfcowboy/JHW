package cn.com.lfcowboy.driver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.Product;

public interface ProductDao {
	public Product getProduct(String productCode);

	public List<Product> getProducts(@Param("product") Product product,
			@Param("page") Page page);

	public boolean updateProduct(Product product);

	public boolean addProduct(Product product);

	public boolean deleteProduct(int id);

	int getTotal(@Param("product") Product product);
}
