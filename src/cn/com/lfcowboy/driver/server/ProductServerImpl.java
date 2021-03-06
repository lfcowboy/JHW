package cn.com.lfcowboy.driver.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.lfcowboy.driver.dao.ProductDao;
import cn.com.lfcowboy.driver.domain.Page;
import cn.com.lfcowboy.driver.domain.Product;

public class ProductServerImpl implements ProductServer {
	private ProductDao productDao;

	public ProductDao getProductDao() {
		return productDao;
	}

	@Autowired
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public Product getProduct(String productCode) {
		return productDao.getProduct(productCode);
	}

	@Override
	public List<Product> getProductsPaged(Product product, Page page) {
		return productDao.getProductsPaged(product, page);
	}

	@Override
	public List<Product> getProductDriverPaged(Product product, Page page) {
		return productDao.getProductDriverPaged(product, page);
	}

	@Override
	public int getProductDriverPagedTotal(Product product) {
		return productDao.getProductDriverPagedTotal(product);
	}

	@Override
	public List<Product> getProducts(Product product) {
		return productDao.getProducts(product);
	}

	@Override
	public boolean updateProduct(Product product) {
		return productDao.updateProduct(product);
	}

	@Override
	public boolean addProduct(Product product) {
		return productDao.addProduct(product);
	}

	@Override
	public boolean deleteProduct(int id) {
		return productDao.deleteProduct(id);
	}

	@Override
	public int getTotal(Product product) {
		return productDao.getTotal(product);
	}

}
