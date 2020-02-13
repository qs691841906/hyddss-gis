package com.sinosoft.ddss.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.sinosoft.ddss.common.entity.Metadata;

public class QueryCollection  extends QueryBase{
	
	private String workType;
	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}
	private Integer dataId;
	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}
	
	private String sql;
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	//表名
	private String type;
	//城市ID
	private String gid;
	//城市名称
	private String cityName;
	//质量等级
	private String qualityGrade;
	//时间类型
	private String timeType;
	//11 产品格式
	private String productFormat;
	//12 轨道号
	private String orbitId;
	//13 产品名称
	private String productName;
	//14 周期号（Cycle号）开始
	private Integer startOrbitCycle;
	//15 周期号（Cycle号）结束
	private Integer endOrbitCycle;
	//16 圈号（Pass号）开始
	private Integer startOrbitPass;
	//17 圈号（Pass号）结束
	private Integer endOrbitPass;
	//18 云盖量开始
	private Integer startCloudPercent;
	//19  云盖量结束
	private Integer endCloudPercent;
	//20  类型（0：无、1：圆、2：多边形、3：行政区域、4：快速查询）
	private String regionalType;
	//21 坐标
	private String ploygon;
	//22 半径
	private BigDecimal radius;
	
	private String placeName;
	
	private String groupType;
	
	private Integer isDelete;
	
	private Integer isSensitive;
	
	private String imagingMode;
	
	private int cycleDifference;
	
	public int getCycleDifference() {
		return cycleDifference;
	}

	public void setCycleDifference(int cycleDifference) {
		this.cycleDifference = cycleDifference;
	}

	public String getImagingMode() {
		return imagingMode;
	}

	public void setImagingMode(String imagingMode) {
		this.imagingMode = imagingMode;
	}

	public Integer getIsSensitive() {
		return isSensitive;
	}

	public void setIsSensitive(Integer isSensitive) {
		this.isSensitive = isSensitive;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

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


	public Integer getStartOrbitCycle() {
		return startOrbitCycle;
	}

	public void setStartOrbitCycle(Integer startOrbitCycle) {
		this.startOrbitCycle = startOrbitCycle;
	}

	public Integer getEndOrbitCycle() {
		return endOrbitCycle;
	}

	public void setEndOrbitCycle(Integer endOrbitCycle) {
		this.endOrbitCycle = endOrbitCycle;
	}

	public Integer getStartOrbitPass() {
		return startOrbitPass;
	}

	public void setStartOrbitPass(Integer startOrbitPass) {
		this.startOrbitPass = startOrbitPass;
	}

	public Integer getEndOrbitPass() {
		return endOrbitPass;
	}

	public void setEndOrbitPass(Integer endOrbitPass) {
		this.endOrbitPass = endOrbitPass;
	}

	public Integer getStartCloudPercent() {
		return startCloudPercent;
	}

	public void setStartCloudPercent(Integer startCloudPercent) {
		this.startCloudPercent = startCloudPercent;
	}

	public Integer getEndCloudPercent() {
		return endCloudPercent;
	}

	public void setEndCloudPercent(Integer endCloudPercent) {
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

	public BigDecimal getRadius() {
		return radius;
	}

	public void setRadius(BigDecimal radius) {
		this.radius = radius;
	}

	
	
	@Override
	public Object getMetadataList() {
		return null;
	}

	@Override
	public Object getGlobalData() {
		return null;
	}

	@Override
	public Integer getMetadataCount() {
		return null;
	}

	@Override
	public Integer queryFromCircleCount() {
		return null;
	}

	@Override
	public List<Metadata> mapQuery() {
		return this.metadataQueryMapper.mapQuery(this.type,this.gid,this.cityName,this.getSatellite(),this.qualityGrade,
				this.timeType,this.getStartTime(),this.getEndTime(),this.getSensor(),this.getProductLevel(),
				this.productFormat,this.orbitId,this.productName,this.startOrbitCycle,this.endOrbitCycle,
				this.startOrbitPass,this.endOrbitPass,this.startCloudPercent,this.endCloudPercent,this.regionalType,
				this.ploygon,this.radius,this.getPage(),this.getPageSize(),this.getSortBy(),this.getSortType(),this.getStartNum());
	}
	@Override
	public List<Metadata> mapQuerySensor() {
		return this.metadataQueryMapper.mapQuerySensor(this.type,this.gid,this.cityName,this.getSatellite(),this.qualityGrade,
				this.timeType,this.getStartTime(),this.getEndTime(),this.getSensor(),this.getProductLevel(),
				this.productFormat,this.orbitId,this.productName,this.startOrbitCycle,this.endOrbitCycle,
				this.startOrbitPass,this.endOrbitPass,this.startCloudPercent,this.endCloudPercent,this.regionalType,
				this.ploygon,this.radius,this.getPage(),this.getPageSize(),this.getSortBy(),this.getSortType(),
				this.getStartNum(),this.getIsDelete(),this.dataId,this.isSensitive,this.imagingMode,this.cycleDifference,this.sql);
	}
	
	@Override
	public Integer updateDataStatusByMapQuery() {
		return this.metadataQueryMapper.updateDataStatusByMapQuery(this.type,this.gid,this.cityName,this.getSatellite(),this.qualityGrade,
				this.timeType,this.getStartTime(),this.getEndTime(),this.getSensor(),this.getProductLevel(),
				this.productFormat,this.orbitId,this.productName,this.startOrbitCycle,this.endOrbitCycle,
				this.startOrbitPass,this.endOrbitPass,this.startCloudPercent,this.endCloudPercent,this.regionalType,
				this.ploygon,this.radius,this.getPage(),this.getPageSize(),this.getSortBy(),this.getSortType(),this.getStartNum(),this.getIsDelete());
	}

	@Override
	public Integer mapQueryCount() {
		return this.metadataQueryMapper.mapQueryCount(this.type,this.gid,this.cityName,this.getSatellite(),this.qualityGrade,
				this.timeType,this.getStartTime(),this.getEndTime(),this.getSensor(),this.getProductLevel(),
				this.productFormat,this.orbitId,this.productName,this.startOrbitCycle,this.endOrbitCycle,
				this.startOrbitPass,this.endOrbitPass,this.startCloudPercent,this.endCloudPercent,this.regionalType,
				this.ploygon,this.radius);
	}
	
	@Override
	public Integer mapQueryCountSensor() {
		return this.metadataQueryMapper.mapQueryCountSensor(this.type,this.gid,this.cityName,this.getSatellite(),this.qualityGrade,
				this.timeType,this.getStartTime(),this.getEndTime(),this.getSensor(),this.getProductLevel(),
				this.productFormat,this.orbitId,this.productName,this.startOrbitCycle,this.endOrbitCycle,
				this.startOrbitPass,this.endOrbitPass,this.startCloudPercent,this.endCloudPercent,this.regionalType,
				this.ploygon,this.radius,this.isDelete,this.isSensitive,this.imagingMode,this.cycleDifference,this.sql);
	}

	@Override
	public List<String> GpoupSatelliteSensorProductLevel() {
		return metadataQueryMapper.GpoupSatelliteSensorProductLevel(regionalType, gid, cityName, this.getSatellite(), qualityGrade, 
				timeType, this.getStartTime(),this.getEndTime(),this.getSensor(),this.getProductLevel(), 
				productFormat, orbitId, productName, startOrbitCycle, endOrbitCycle, 
				startOrbitPass, endOrbitPass, startCloudPercent, endCloudPercent, regionalType, 
				ploygon, radius,groupType);
	}

	@Override
	public Map<String, String> mapQueryCountSum() {
		return this.metadataQueryMapper.mapQueryCountSum(this.type,this.gid,this.cityName,this.getSatellite(),this.qualityGrade,
				this.timeType,this.getStartTime(),this.getEndTime(),this.getSensor(),this.getProductLevel(),
				this.productFormat,this.orbitId,this.productName,this.startOrbitCycle,this.endOrbitCycle,
				this.startOrbitPass,this.endOrbitPass,this.startCloudPercent,this.endCloudPercent,this.regionalType,
				this.ploygon,this.radius);
	}
	
	@Override
	public Map<String, String> mapQueryCountSumSensor() {
		return this.metadataQueryMapper.mapQueryCountSumSensor(this.type,this.gid,this.cityName,this.getSatellite(),this.qualityGrade,
				this.timeType,this.getStartTime(),this.getEndTime(),this.getSensor(),this.getProductLevel(),
				this.productFormat,this.orbitId,this.productName,this.startOrbitCycle,this.endOrbitCycle,
				this.startOrbitPass,this.endOrbitPass,this.startCloudPercent,this.endCloudPercent,this.regionalType,
				this.ploygon,this.radius,this.imagingMode,this.cycleDifference);
	}


}
