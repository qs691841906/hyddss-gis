package com.sinosoft.ddss.model;

import com.sinosoft.ddss.common.base.entity.BasePaginationQuery;

/**
 * <pre>
 * Project name：ddss-gis    
 * Title：GeoQueryBase    
 * Description：    
 * Author：sen_kung   
 * Create date：2018年5月3日 上午10:06:07    
 * Author：sen_kung      
 * Update date：2018年5月3日 上午10:06:07    
 * Description：       
 * &#64;version
 * </pre>
 */

public class GeoQueryBase extends BasePaginationQuery{
	// 表名
	private String type;
	// 城市ID
	private String gid;
	// 城市名称
	private String cityName;
	// 质量等级
	private String qualityGrade;
	// 时间类型
	private String timeType;
	// 11 产品格式
	private String productFormat;
	// 12 轨道号
	private String orbitId;
	// 13 产品名称
	private String productName;
	// 14 周期号（Cycle号）开始
	private String startOrbitCycle;
	// 15 周期号（Cycle号）结束
	private String endOrbitCycle;
	// 16 圈号（Pass号）开始
	private String startOrbitPass;
	// 17 圈号（Pass号）结束
	private String endOrbitPass;
	// 18 云盖量开始
	private String startCloudPercent;
	// 19 云盖量结束
	private String endCloudPercent;
	// 20 类型（0：无、1：圆、2：多边形、3：行政区域）
	private String regionalType;
	// 21 坐标
	private String ploygon;
	// 22 半径
	private String radius;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getQualityGrade() {
		return qualityGrade;
	}
	public void setQualityGrade(String qualityGrade) {
		this.qualityGrade = qualityGrade;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getProductFormat() {
		return productFormat;
	}
	public void setProductFormat(String productFormat) {
		this.productFormat = productFormat;
	}
	public String getOrbitId() {
		return orbitId;
	}
	public void setOrbitId(String orbitId) {
		this.orbitId = orbitId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStartOrbitCycle() {
		return startOrbitCycle;
	}
	public void setStartOrbitCycle(String startOrbitCycle) {
		this.startOrbitCycle = startOrbitCycle;
	}
	public String getEndOrbitCycle() {
		return endOrbitCycle;
	}
	public void setEndOrbitCycle(String endOrbitCycle) {
		this.endOrbitCycle = endOrbitCycle;
	}
	public String getStartOrbitPass() {
		return startOrbitPass;
	}
	public void setStartOrbitPass(String startOrbitPass) {
		this.startOrbitPass = startOrbitPass;
	}
	public String getEndOrbitPass() {
		return endOrbitPass;
	}
	public void setEndOrbitPass(String endOrbitPass) {
		this.endOrbitPass = endOrbitPass;
	}
	public String getStartCloudPercent() {
		return startCloudPercent;
	}
	public void setStartCloudPercent(String startCloudPercent) {
		this.startCloudPercent = startCloudPercent;
	}
	public String getEndCloudPercent() {
		return endCloudPercent;
	}
	public void setEndCloudPercent(String endCloudPercent) {
		this.endCloudPercent = endCloudPercent;
	}
	public String getRegionalType() {
		return regionalType;
	}
	public void setRegionalType(String regionalType) {
		this.regionalType = regionalType;
	}
	public String getPloygon() {
		return ploygon;
	}
	public void setPloygon(String ploygon) {
		this.ploygon = ploygon;
	}
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	@Override
	public String toString() {
		return "GeoQueryBase [type=" + type + ", gid=" + gid + ", cityName=" + cityName + ", qualityGrade="
				+ qualityGrade + ", timeType=" + timeType + ", productFormat=" + productFormat + ", orbitId=" + orbitId
				+ ", productName=" + productName + ", startOrbitCycle=" + startOrbitCycle + ", endOrbitCycle="
				+ endOrbitCycle + ", startOrbitPass=" + startOrbitPass + ", endOrbitPass=" + endOrbitPass
				+ ", startCloudPercent=" + startCloudPercent + ", endCloudPercent=" + endCloudPercent
				+ ", regionalType=" + regionalType + ", ploygon=" + ploygon + ", radius=" + radius + "]";
	}
}
