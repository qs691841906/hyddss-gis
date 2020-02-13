package com.sinosoft.ddss.model;

import java.util.List;
import java.util.Map;

import com.sinosoft.ddss.common.entity.Metadata;

public class QueryByDistrict extends QueryBase {

	private String districtType;
	private Integer districtId;

	public String getDistrictType() {
		return districtType;
	}

	public void setDistrictType(String districtType) {
		this.districtType = districtType;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	@Override
	public Object getMetadataList() {
		// TODO Auto-generated method stub
		return this.metadataQueryMapper.queryFromDistrict(this.districtType,
				this.districtId, this.getSensor());
	}

	@Override
	public Object getGlobalData() {
		// TODO Auto-generated method stub
		return this.geoMapper.getGlobalDataFromDistrict(this.districtType,
				this.districtId, this.getSensor());
	}

	@Override
	public Integer getMetadataCount() {
		// TODO Auto-generated method stub
			return null;
			
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
