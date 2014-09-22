package cn.com.lfcowboy.driver.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class Version {
	int id;
	int driverId;
	float version;
	String sequence;
	MultipartFile driverFile;
	String filePath;
	String remark;
	Date addTime;
	
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
	public float getVersion() {
		return version;
	}
	public void setVersion(float version) {
		this.version = version;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
}
