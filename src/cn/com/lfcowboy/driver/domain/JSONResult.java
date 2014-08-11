package cn.com.lfcowboy.driver.domain;

import java.util.List;

public class JSONResult {
	private List<?> items;
	private String total;
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public List<?> getItems() {
		return items;
	}
	public void setItems(List<?> items) {
		this.items = items;
	}

}
