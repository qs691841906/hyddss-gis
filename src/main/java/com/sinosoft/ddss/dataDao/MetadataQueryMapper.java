package com.sinosoft.ddss.dataDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.sinosoft.ddss.common.entity.Metadata;

/**
 * 
 * @author sanyo
 * @since 2018年3月17日 下午9:44:05
 * @version 1.0.0
 * @TODO
 */
public interface MetadataQueryMapper {

	//根据ploygon字符串来检索数据（POINT(xxx)、PLOYGON(xxx)...）
	List<Metadata> queryFromPloygon(String ploygon, String sensor, Integer pageSize, Integer offsetNum,
			String startTime, String endTime, String satellite, String productLevel, Integer cloudCoverage,String sortBy, String sortType);

	//根据行政的类型（国家、省、市、县）以及在空间表里的ID来检索数据
	List<Metadata> queryFromDistrict(String districtType, Integer districtId,
			String sensor);

	//根据点和半径来检索数据
	List<Metadata> queryFromCircle(String pointPloygon, BigDecimal radius,
			String sensor);

	//地图查询
	List<Metadata> mapQuery(String type,String gid,String cityName,String satellite,String qualityGrade,
			String timeType,String startTime,String endTime,String sensor,String productLevel,
			String productFormat,String orbitId,String productName,Integer startOrbitCycle,Integer endOrbitCycle,
			Integer startOrbitPass,Integer endOrbitPass,Integer startCloudPercent,Integer endCloudPercent,String regionalType,
			String ploygon,BigDecimal radius,Integer page,Integer pageSize,String sortBy, String sortType, Integer startNum);
	List<Metadata> mapQuerySensor(String type,String gid,String cityName,String satellite,String qualityGrade,
			String timeType,String startTime,String endTime,String sensor,String productLevel,
			String productFormat,String orbitId,String productName,Integer startOrbitCycle,Integer endOrbitCycle,
			Integer startOrbitPass,Integer endOrbitPass,Integer startCloudPercent,Integer endCloudPercent,String regionalType,
			String ploygon,BigDecimal radius,Integer page,Integer pageSize,String sortBy, String sortType, Integer startNum,
			Integer isDelete,Integer dataId,Integer isSensitive,String imagingMode,Integer cycleDifference,String sql);
	
	Integer updateDataStatusByMapQuery(String type,String gid,String cityName,String satellite,String qualityGrade,
			String timeType,String startTime,String endTime,String sensor,String productLevel,
			String productFormat,String orbitId,String productName,Integer startOrbitCycle,Integer endOrbitCycle,
			Integer startOrbitPass,Integer endOrbitPass,Integer startCloudPercent,Integer endCloudPercent,String regionalType,
			String ploygon,BigDecimal radius,Integer page,Integer pageSize,String sortBy, String sortType, Integer startNum,Integer isDelete);
	
	
	//地图查询行数
	Integer mapQueryCount(String type,String gid,String cityName,String satellite,String qualityGrade,
			String timeType,String startTime,String endTime,String sensor,String productLevel,
			String productFormat,String orbitId,String productName,Integer startOrbitCycle,Integer endOrbitCycle,
			Integer startOrbitPass,Integer endOrbitPass,Integer startCloudPercent,Integer endCloudPercent,String regionalType,
			String ploygon,BigDecimal radius);
	Integer mapQueryCountSensor(String type,String gid,String cityName,String satellite,String qualityGrade,
			String timeType,String startTime,String endTime,String sensor,String productLevel,
			String productFormat,String orbitId,String productName,Integer startOrbitCycle,Integer endOrbitCycle,
			Integer startOrbitPass,Integer endOrbitPass,Integer startCloudPercent,Integer endCloudPercent,String regionalType,
			String ploygon,BigDecimal radius,Integer isDelete,Integer isSensitive,String imagingMode,Integer cycleDifference,String sql);
	//根据点和半径来检索数据数量
	Integer queryFromCircleCount(String pointPloygon, BigDecimal radius,
			String sensor);
	
	//根据ploygon字符串来检索数据量
	Integer getMetaDataCount(String ploygon, String sensor,
			String startTime, String endTime, String satellite, String productLevel, Integer cloudCoverage);

	List<Metadata> GetByDataId(List<Integer> list);

	List<String> GpoupSatelliteSensorProductLevel(String type,String gid,String cityName,String satellite,String qualityGrade,
			String timeType,String startTime,String endTime,String sensor,String productLevel,
			String productFormat,String orbitId,String productName,Integer startOrbitCycle,Integer endOrbitCycle,
			Integer startOrbitPass,Integer endOrbitPass,Integer startCloudPercent,Integer endCloudPercent,String regionalType,
			String ploygon,BigDecimal radius,String groupType);

	Map<String, String> mapQueryCountSum(String type, String gid, String cityName, String satellite,
			String qualityGrade, String timeType, String startTime, String endTime, String sensor, String productLevel,
			String productFormat, String orbitId, String productName, Integer startOrbitCycle, Integer endOrbitCycle,
			Integer startOrbitPass, Integer endOrbitPass, Integer startCloudPercent, Integer endCloudPercent,
			String regionalType, String ploygon, BigDecimal radius);
	Map<String, String> mapQueryCountSumSensor(String type, String gid, String cityName, String satellite,
			String qualityGrade, String timeType, String startTime, String endTime, String sensor, String productLevel,
			String productFormat, String orbitId, String productName, Integer startOrbitCycle, Integer endOrbitCycle,
			Integer startOrbitPass, Integer endOrbitPass, Integer startCloudPercent, Integer endCloudPercent,
			String regionalType, String ploygon, BigDecimal radius,String imagingMode,Integer cycleDifference);

}
