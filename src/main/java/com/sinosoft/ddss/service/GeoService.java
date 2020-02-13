package com.sinosoft.ddss.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.ddss.common.entity.Additional;
import com.sinosoft.ddss.common.entity.Metadata;
import com.sinosoft.ddss.model.GlobalData;
import com.sinosoft.ddss.model.Place;
import com.sinosoft.ddss.model.QueryBase;
import com.sinosoft.ddss.model.QueryCollection;

/**
 * 
 * @author sanyo
 * @since 2018年3月17日 下午9:44:14
 * @version 1.0.0
 * @TODO
 */
public interface GeoService {

	public BigDecimal getDistance(String point1Str, String point2Str);

	public BigDecimal getAreaFromPloygon(String ploygonStr);

	public String getDistrictPloygon(String type, Integer id);

	public List<Metadata> queryMetadata(QueryBase queryBase);

	public GlobalData getGlobalData(QueryBase queryBase);

	public Integer getMetaDateCount(QueryBase queryBase);

	public List<Place> getPlaceByCode(String type, Integer id, String code, String letter);

	public Integer queryFromCircleCount(QueryBase queryBase);
	
	public List<Metadata> mapQuery(QueryBase queryBase);
	
	public List<Metadata> mapQuerySensor(QueryBase queryBase);
	
	public List<Metadata> GetByDataId(List<Integer> data_id);
	
	public Integer mapQueryCount(QueryBase queryBase);
	
	public Integer mapQueryCountSensor(QueryBase queryBase);

	public GlobalData getGlobal(Additional bean);

	public String getDistrictByName(QueryCollection collection);

	public List<Place> getPlacesByName(QueryCollection collection);

	public List<String> GpoupSatelliteSensorProductLevel(QueryBase queryModel);

	public Map<String, String> mapQueryCountSum(QueryBase queryBase);
	
	public Map<String, String> mapQueryCountSumSensor(QueryBase queryBase);

	public List<HashMap<String, Object>> getDistrictListByName(QueryCollection collection);
	
	/**
	 * 根据id批量修改数据删除状态
	 * @param map
	 * @return
	 */
	public int updateIsDeleteById(Map<String, Object> map);
	
	public int updateDataStatusByMapQuery(QueryBase queryBase);

}
