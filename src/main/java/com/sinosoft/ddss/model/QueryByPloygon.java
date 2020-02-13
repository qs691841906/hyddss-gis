package com.sinosoft.ddss.model;

import java.util.List;
import java.util.Map;

import com.sinosoft.ddss.common.entity.Metadata;

public class QueryByPloygon extends QueryBase {

	private String ploygon;

	public String getPloygon() {
		return ploygon;
	}

	public void setPloygon(String ploygon) {
		this.ploygon = ploygon;
	}

	@Override
	public Object getMetadataList() {
		// TODO Auto-generated method stub
		return this.metadataQueryMapper.queryFromPloygon(this.getPloygon(),
				this.getSensor(),this.getPageSize(),this.getStartNum(), 
				this.getStartTime(),this.getEndTime(),this.getSatellite(),
				this.getProductLevel(),this.getCloudCoverage(),this.getSortBy(), this.getSortType());
	}

	@Override
	public Object getGlobalData() {
		// TODO Auto-generated method stub
		return this.geoMapper.getGlobalDataFromPloygon(this.getPloygon(),
				this.getSensor());
	}

	@Override
	public Integer getMetadataCount() {
		
			return this.metadataQueryMapper.getMetaDataCount(this.getPloygon(),
				this.getSensor(),this.getStartTime(),this.getEndTime(),this.getSatellite(),
				this.getProductLevel(),this.getCloudCoverage());
			
	}

	@Override
	public Integer queryFromCircleCount() {
		// TODO Auto-generated method stub
		return null;
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
