package cn.com.lfcowboy.driver.domain;

public class Page {
	private int limit;
	private int offset;
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}	
}
