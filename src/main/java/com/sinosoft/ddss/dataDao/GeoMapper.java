package com.sinosoft.ddss.dataDao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sinosoft.ddss.common.entity.Additional;
import com.sinosoft.ddss.common.entity.Metadata;
import com.sinosoft.ddss.model.GlobalData;
import com.sinosoft.ddss.model.Place;
import com.sinosoft.ddss.model.QueryCollection;

/**
 * 
 * @author sanyo
 * @since 2018年3月17日 下午9:44:05
 * @version 1.0.0
 * @TODO
 */
public interface GeoMapper {

	BigDecimal getArea(@Param("metadata") Metadata metadata);

	BigDecimal getAreaFromPloygon(String ploygon);
	
	String getDistrictPloygon(String type, Integer id);
	
	//求两点之间的距离
	BigDecimal getDistance(String point1Ploygon,String point2Ploygon);
	
	//求覆盖比、面积、union ploygon str（按照ploygon）
	GlobalData getGlobalDataFromPloygon(String ploygon, String sensor);
	
	//求覆盖比、面积、union ploygon str（按照行政区）
	GlobalData getGlobalDataFromDistrict(String districtType, Integer districtId,
			String sensor);
	
	//求覆盖比、面积、union ploygon str（按照点 和 半径）
	GlobalData getGlobalDataFromCircle(String pointPloygon, BigDecimal radius,
			String sensor);

	//根据code 获取省市县
	List<Place> getPlaceByCode(String type, Integer id, String code, String letter);

	//求覆盖比
	GlobalData getGlobal(Additional bean);
	//根据名字查询区域经纬度
	String getDistrictByName(QueryCollection collection);
	//根据名字查询区域
	List<Place> getPlacesByName(QueryCollection collection);
	//根据名字查询区域经纬度
	List<HashMap<String, Object>> getDistrictListByName(QueryCollection collection);

	int updateIsDeleteById(Map<String, Object> map);
}
