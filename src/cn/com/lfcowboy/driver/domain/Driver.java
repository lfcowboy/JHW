package cn.com.lfcowboy.driver.domain;

public class Driver {
	int id;
	String name;
	int productId;
	String productCode;
	Integer engineerId;
	String engineerName;
	String chipCode;
	Integer customerId;
	String customerName;
	String lastVersion;

	public String getLastVersion() {
		return lastVersion;
	}

	public void setLastVersion(String lastVersion) {
		this.lastVersion = lastVersion;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEngineerName() {
		return engineerName;
	}

	public void setEngineerName(String engineerName) {
		this.engineerName = engineerName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getEngineerId() {
		return engineerId;
	}

	public void setEngineerId(Integer engineerId) {
		this.engineerId = engineerId;
	}

	public String getChipCode() {
		return chipCode;
	}

	public void setChipCode(String chipCode) {
		this.chipCode = chipCode;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
}
