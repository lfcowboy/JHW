package cn.com.lfcowboy.driver.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class Version {
	int id;
	int driverId;
	String version;
	String sequence;
	MultipartFile driverFile;
	String fileName;
	String remark;
	Date addTime;
	int burnSum;
	
	public MultipartFile getDriverFile() {
		return driverFile;
	}

	public void setDriverFile(MultipartFile driverFile) {
		this.driverFile = driverFile;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getFileName() {
		return fileName;
	}

	public void setFilePath(String fileName) {
		this.fileName = fileName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAddTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(addTime);
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public int getBurnSum() {
		return burnSum;
	}

	public void setBurnSum(int burnSum) {
		this.burnSum = burnSum;
	}

}
