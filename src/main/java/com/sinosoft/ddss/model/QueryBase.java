package com.sinosoft.ddss.model;

import java.util.List;
import java.util.Map;

import com.sinosoft.ddss.common.base.entity.BasePaginationQuery;
import com.sinosoft.ddss.common.entity.Metadata;
import com.sinosoft.ddss.dataDao.GeoMapper;
import com.sinosoft.ddss.dataDao.MetadataQueryMapper;

public abstract class QueryBase extends BasePaginationQuery {

	private String sensor;

	private String startTime;

	private String endTime;

	private String satellite;

	private String productLevel;

	private Integer cloudCoverage;

	public MetadataQueryMapper metadataQueryMapper;

	public GeoMapper geoMapper;

	public MetadataQueryMapper getMetadataQueryMapper() {
		return metadataQueryMapper;
	}

	public void setMetadataQueryMapper(MetadataQueryMapper metadataQueryMapper) {
		this.metadataQueryMapper = metadataQueryMapper;
	}

	public GeoMapper getGeoMapper() {
		return geoMapper;
	}

	public void setGeoMapper(GeoMapper geoMapper) {
		this.geoMapper = geoMapper;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}

	public String getStartTime() {

		return startTime;
	}

	public void setStartTime(String startTime) {

		this.startTime = startTime;
	}

	public String getEndTime() {

		return endTime;
	}

	public void setEndTime(String endTime) {

		this.endTime = endTime;
	}

	public String getSatellite() {

		return satellite;
	}

	public void setSatellite(String satellite) {

		this.satellite = satellite;
	}

	public String getProductLevel() {

		return productLevel;
	}

	public void setProductLevel(String productLevel) {

		this.productLevel = productLevel;
	}

	public Integer getCloudCoverage() {

		return cloudCoverage;
	}

	public void setCloudCoverage(Integer cloudCoverage) {

		this.cloudCoverage = cloudCoverage;
	}

	public abstract Object getMetadataList();

	public abstract Object getGlobalData();

	public abstract Integer getMetadataCount();

	public abstract Integer queryFromCircleCount();
	
	public abstract List<Metadata> mapQuery();
	
	public abstract List<Metadata> mapQuerySensor();
	
	public abstract Integer updateDataStatusByMapQuery();

	public abstract Integer mapQueryCount();
	
	public abstract Integer mapQueryCountSensor();
	
	public abstract Map<String, String> mapQueryCountSum();
	
	public abstract Map<String, String> mapQueryCountSumSensor();

	public abstract List<String> GpoupSatelliteSensorProductLevel();
}
