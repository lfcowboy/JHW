package cn.com.lfcowboy.driver.domain;

import java.util.Date;

public class Product {
	private int id;
	private String code;
	private Integer customerId;
	private String customerName;
	private String driverName;
	private String chipCode;
	private String engineerName;
	private float version;
	private Date addTime;
	private String sequence;
	String remark;

	public float getVersion() {
		return version;
	}

	public void setVersion(float version) {
		this.version = version;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getChipCode() {
		return chipCode;
	}

	public void setChipCode(String chipCode) {
		this.chipCode = chipCode;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
}
