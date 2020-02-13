package com.sinosoft.ddss.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.ddss.common.entity.Additional;
import com.sinosoft.ddss.common.entity.Metadata;
import com.sinosoft.ddss.dataDao.GeoMapper;
import com.sinosoft.ddss.dataDao.MetadataQueryMapper;
import com.sinosoft.ddss.model.GlobalData;
import com.sinosoft.ddss.model.Place;
import com.sinosoft.ddss.model.QueryBase;
import com.sinosoft.ddss.model.QueryCollection;
import com.sinosoft.ddss.service.GeoService;

/**
 * 
 * @author sanyo
 * @since 2018年3月17日 下午9:44:10
 * @version 1.0.0
 * @TODO
 */
@Service
public class GeoServiceImpl implements GeoService {

	@Autowired
	private GeoMapper geoMapper;
	@Autowired
	private MetadataQueryMapper metadataQueryMapper;

	@Override
	public BigDecimal getAreaFromPloygon(String ploygonStr) {
		// TODO Auto-generated method stub
		return geoMapper.getAreaFromPloygon(ploygonStr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Metadata> queryMetadata(QueryBase queryBase) {
		queryBase.setMetadataQueryMapper(metadataQueryMapper);
		return (List<Metadata>) queryBase.getMetadataList();
	}

	@Override
	public GlobalData getGlobalData(QueryBase queryBase) {
		// TODO Auto-generated method stub
		queryBase.setGeoMapper(geoMapper);
		return (GlobalData) queryBase.getGlobalData();
	}

	@Override
	public BigDecimal getDistance(String point1Str, String point2Str) {
		// TODO Auto-generated method stub
		return geoMapper.getDistance(point1Str, point2Str);
	}

	@Override
	public String getDistrictPloygon(String type, Integer id) {
		// TODO Auto-generated method stub
		return geoMapper.getDistrictPloygon(type, id);
	}

	@Override
	public Integer getMetaDateCount(QueryBase queryBase) {
		// TODO Auto-generated method stub
		queryBase.setMetadataQueryMapper(metadataQueryMapper);
		return queryBase.getMetadataCount();
			
	}
	
	@Override
	public Integer queryFromCircleCount(QueryBase queryBase) {
		// TODO Auto-generated method stub
		queryBase.setMetadataQueryMapper(metadataQueryMapper);
		return queryBase.queryFromCircleCount();
			
	}

	@Override
	public List<Place> getPlaceByCode(String type, Integer id, String code, String letter) {
		// TODO Auto-generated method stub
		List<Place> listPlace = geoMapper.getPlaceByCode(type, id, code, letter);
		return listPlace;
			
	}

	@Override
	public List<Metadata> mapQuery(QueryBase queryBase) {
		queryBase.setMetadataQueryMapper(metadataQueryMapper);
		return (List<Metadata>) queryBase.mapQuery();
	}
	
	@Override
	public List<Metadata> mapQuerySensor(QueryBase queryBase) {
		queryBase.setMetadataQueryMapper(metadataQueryMapper);
		return (List<Metadata>) queryBase.mapQuerySensor();
	}
	
	@Override
	public int updateDataStatusByMapQuery(QueryBase queryBase) {
		queryBase.setMetadataQueryMapper(metadataQueryMapper);
		return  queryBase.updateDataStatusByMapQuery();
	}

	@Override
	public Integer mapQueryCount(QueryBase queryBase) {
		queryBase.setMetadataQueryMapper(metadataQueryMapper);
		return queryBase.mapQueryCount();
	}

	@Override
	public Integer mapQueryCountSensor(QueryBase queryBase) {
		queryBase.setMetadataQueryMapper(metadataQueryMapper);
		return queryBase.mapQueryCountSensor();
	}
	
	@Override
	public Map<String, String> mapQueryCountSum(QueryBase queryBase) {
		queryBase.setMetadataQueryMapper(metadataQueryMapper);
		return queryBase.mapQueryCountSum();
	}
	
	@Override
	public Map<String, String> mapQueryCountSumSensor(QueryBase queryBase) {
		queryBase.setMetadataQueryMapper(metadataQueryMapper);
		return queryBase.mapQueryCountSumSensor();
	}

	@Override
	public List<Metadata> GetByDataId(List<Integer> data_id) {
		return metadataQueryMapper.GetByDataId(data_id);
	}

	@Override
	public GlobalData getGlobal(Additional bean) {
		return geoMapper.getGlobal(bean);
	}

	@Override
	public String getDistrictByName(QueryCollection collection) {
		// TODO Auto-generated method stub
		long start = new Date().getTime();
		String districtByName = geoMapper.getDistrictByName(collection);
		long end = new Date().getTime();
		System.out.println(end - start);
		return districtByName;
	}

	@Override
	public List<Place> getPlacesByName(QueryCollection collection) {
		// TODO Auto-generated method stub
		return geoMapper.getPlacesByName(collection);
	}

	@Override
	public List<String> GpoupSatelliteSensorProductLevel(QueryBase queryBase) {
		// TODO Auto-generated method stub

		queryBase.setMetadataQueryMapper(metadataQueryMapper);
		return queryBase.GpoupSatelliteSensorProductLevel();
	}

	@Override
	public List<HashMap<String, Object>> getDistrictListByName(QueryCollection collection) {
		// TODO Auto-generated method stub
		return geoMapper.getDistrictListByName(collection);
	}
	
	@Override
	public int updateIsDeleteById(Map<String, Object> map){
		
		return geoMapper.updateIsDeleteById(map);
		
	}

}
