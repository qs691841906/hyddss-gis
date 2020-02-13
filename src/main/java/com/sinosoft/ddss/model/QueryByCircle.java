package com.sinosoft.ddss.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.sinosoft.ddss.common.entity.Metadata;

public class QueryByCircle extends QueryBase {

	private String pointPloygon;
	private BigDecimal radius;

	public String getPointPloygon() {
		return pointPloygon;
	}

	public void setPointPloygon(String pointPloygon) {
		this.pointPloygon = pointPloygon;
	}

	public BigDecimal getRadius() {
		return radius;
	}

	public void setRadius(BigDecimal radius) {
		this.radius = radius;
	}

	@Override
	public Object getMetadataList() {
		// TODO Auto-generated method stub
		return this.metadataQueryMapper.queryFromCircle(this.pointPloygon,
				this.radius, this.getSensor());
	}

	@Override
	public Object getGlobalData() {
		// TODO Auto-generated method stub
		return this.geoMapper.getGlobalDataFromCircle(this.pointPloygon,
				this.radius, this.getSensor());
	}
	
	@Override
	public Integer queryFromCircleCount(){
		return this.metadataQueryMapper.queryFromCircleCount(this.pointPloygon, this.radius, this.getSensor());
	}

	@Override
	public Integer getMetadataCount() {
		// TODO Auto-generated method stub
			return this.metadataQueryMapper.queryFromCircleCount(this.pointPloygon, this.radius, this.getSensor());
			
	}

	@Override
	public List<Metadata> mapQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer mapQueryCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> GpoupSatelliteSensorProductLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> mapQueryCountSum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Metadata> mapQuerySensor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer mapQueryCountSensor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> mapQueryCountSumSensor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateDataStatusByMapQuery() {
		// TODO Auto-generated method stub
		return null;
	}

}
