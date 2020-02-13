package com.sinosoft.ddss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ddss.common.constant.Constant;
import com.sinosoft.ddss.common.entity.QueryHistory;
import com.sinosoft.ddss.common.entity.SysResource;
import com.sinosoft.ddss.common.entity.User;
import com.sinosoft.ddss.common.util.JsonUtils;
import com.sinosoft.ddss.jedis.JedisClient;
import com.sinosoft.ddss.service.DecryptToken;
import com.sinosoft.ddss.service.QueryHistoryService;

/**
 * @author li_jiazhi
 * @create 2018年3月26日下午4:47:33 历史记录查询接口控制层
 */
@RestController
public class QueryHistoryController {

	private static Logger LOGGER = LoggerFactory.getLogger(QueryHistoryController.class);

	@Autowired
	private QueryHistoryService queryHistoryService;
	
	@Autowired
	private DecryptToken decryptToken;
	@Autowired
	private JedisClient jedisClient;

	/**
	 * @param id
	 * @return
	 * @author li_jiazhi
	 * @create 2018年3月26日下午4:47:24 根据用户名查询历史记录
	 */
	@RequestMapping(value="/oauth/queryHistory",method=RequestMethod.POST)
	public String queryHistoryById(String token) {
		// 常见返回值对象
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取id名称
		if (StringUtils.isBlank(token)) {
			map.put("msg", "token is null");
			map.put("status", false);
			return JsonUtils.objectToJson(map);
		}
		User user = decryptToken.decyptToken(token);
		if(user == null){
			map.put("msg", "token is expired");
			map.put("status", false);
			return JsonUtils.objectToJson(map);
		}
		try {
			List<QueryHistory> list = queryHistoryService.selectByUserId(user.getUserId());
			map.put("data", list);
		} catch (NumberFormatException e) {
			LOGGER.error("Query History list has exception, caused by "+e.getMessage(),Constant.SYSTEM);
			map.put("msg", "error");
			map.put("status", false);
			return JsonUtils.objectToJson(map);
		}
		return JsonUtils.objectToJson(map);
	}

	/**
	 * @param id
	 * @return
	 * @author li_jiazhi
	 * @create 2018年3月26日下午4:48:01 根据id删除历史记录
	 */
	@RequestMapping("/oauth/queryHistory/delete")
	public String delete(String id) {
		// 常见返回值对象
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(id + "")) {
			map.put("msg", "Id null");
			map.put("status", false);
			return JsonUtils.objectToJson(map);
		}
		try {
			Integer result = this.queryHistoryService.deleteByPrimaryKey(Long.parseLong(id));
			if (result > 0) {
				map.put("status", true);
				return JsonUtils.objectToJson(map);
			} else {
				map.put("msg", "delete failure");
				map.put("status", false);
				return JsonUtils.objectToJson(map);
			}
		} catch (Exception e) {
			LOGGER.error("Delete History list has exception, caused by "+e.getMessage(),Constant.SYSTEM);
			map.put("msg", "error");
			map.put("status", false);
			return JsonUtils.objectToJson(map);
		}
	}

	/**
	 * @param record
	 * @return
	 * @author li_jiazhi
	 * @create 2018年3月26日下午4:48:16 修改历史记录
	 */
	@RequestMapping("/oauth/queryHistory/update")
	public String update(@RequestBody QueryHistory record) {
		// 常见返回值对象
		Map<String, Object> map = new HashMap<String, Object>();
		if (record == null) {
			map.put("msg", "param is null");
			map.put("status", false);
			return JsonUtils.objectToJson(map);
		}
		try {
			int result = queryHistoryService.updateByPrimaryKeySelective(record);
			if (result > 0) {
				map.put("status", true);
				return JsonUtils.objectToJson(map);
			} else {
				map.put("msg", "update failure");
				map.put("status", false);
				return JsonUtils.objectToJson(map);
			}
		} catch (Exception e) {
			LOGGER.error("Update History list has exception, caused by "+e.getMessage(),Constant.SYSTEM);
			map.put("msg", "error");
			map.put("status", false);
			return JsonUtils.objectToJson(map);
		}
	}

	/**
	 * @param record
	 * @return
	 * @author li_jiazhi
	 * @create 2018年3月26日下午4:48:29 新增历史记录
	 */
	@RequestMapping("/oauth/queryHistory/addQueryHistory")
	public String addQueryHistory(@RequestBody QueryHistory record) {
		// 常见返回值对象
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(record.getToken())) {
			map.put("msg", "token is null");
			map.put("status", false);
			return JsonUtils.objectToJson(map);
		}
		try {
			int result = queryHistoryService.insertSelective(record);
			if (result > 0) {
				map.put("status", true);
				return JsonUtils.objectToJson(map);
			} else {
				map.put("msg", "add failure");
				map.put("status", false);
				return JsonUtils.objectToJson(map);
			}
		} catch (Exception e) {
			LOGGER.error("Add History list has exception, caused by "+e.getMessage(),Constant.SYSTEM);
			map.put("msg", "error");
			map.put("status", false);
			return JsonUtils.objectToJson(map);
		}
	}

	/**
	 * @param record
	 * @return
	 * @author 
	 * @create 修改查询
	 */
	@RequestMapping("/oauth/queryHistory/selectQueryHistoryById")
	public String selectQueryHistoryById(@RequestBody QueryHistory record) {
//		public String selectQueryHistoryById( QueryHistory record) {
		// 常见返回值对象
		Map<String, Object> map = new HashMap<String, Object>();
		
		String token = record.getToken();
//		如果token为空返回前台
		if(StringUtils.isBlank(token)){
			map.put("status", false);
			map.put("msg", "Token is null");
			return JsonUtils.objectToJson(map);
		}
		String is = jedisClient.get(token);
//		权限集合
		String salliteSource = "";
		String sensorSource = "";
		String levelSource = "";
		if(StringUtils.isBlank(is)){
			map.put("status", false);
			map.put("msg", "Token already invalid, Please login");
			return JsonUtils.objectToJson(map);
		}else{
//			获取用户的权限
			User user = JsonUtils.jsonToPojo(is, User.class);
			List<SysResource> sysResource_list = user.getSysSource();
			for(SysResource sysResource:sysResource_list){
				String url = sysResource.getUrl();
//				获取卫星权限
				if("satellite".equals(url)){
					salliteSource+=sysResource.getEnname()+",";
				}else if("sensor".equals(url)){
					sensorSource+=sysResource.getEnname()+",";
				}else if("level".equals(url)){
					levelSource+=sysResource.getEnname()+",";
				}
				
			}
			salliteSource = salliteSource.substring(0, salliteSource.length()-1);
			sensorSource = sensorSource.substring(0, sensorSource.length()-1);
			levelSource = levelSource.substring(0, levelSource.length()-1);
		}

		
		if (record.getId()==null) {
			map.put("msg", "id is null");
			map.put("status", false);
			return JsonUtils.objectToJson(map);
		}
		try {
			QueryHistory queryHistory = queryHistoryService.selectQueryHistoryById(record.getId());
			if(null==queryHistory){
				map.put("msg", "queryHistory id null");
				map.put("status", false);
				return JsonUtils.objectToJson(map);
			}
			
//			获取卫星和级别
			String satellites = queryHistory.getSatellite();
			String productLevel = queryHistory.getProductLevel();
			String sensor = queryHistory.getSensor();
			


//			获取所有系列，并根据系列获取卫星
			String serieses = jedisClient.get("Series");
			for(String series:serieses.split(",")){
				String satelliteds = jedisClient.get(series+"Series");
//				根据卫星判断系列
				if(satelliteds.indexOf(satellites.split(",")[0])>-1){
					map.put("series", series);

					if(null!=satelliteds&&satelliteds.length()>0){
						List<Object> satellitedList = new ArrayList<Object>();
//						循环系列对应的卫星
						for(String satellited:satelliteds.split(",")){
							if(null!=salliteSource&&salliteSource.length()>0){
								if(salliteSource.indexOf(satellited)>-1){
									Map<String,String> satellitMap = new HashMap<String,String>();
									satellitMap.put("satellite", satellited);
									if(satellites.indexOf(satellited)>-1){
										satellitMap.put("checked", "true");
									}
									satellitedList.add(satellitMap);
								}
							}
							
						}
						map.put("sallited", satellitedList);
					}else{
						map.put("status", false);
						map.put("msg", "Sallited is null");
						return JsonUtils.objectToJson(map);
					}
				}
			}
			
//			根据系列获取传感器，并显示出已选中的
			String series = String.valueOf(map.get("series"));
			String sensors = jedisClient.get(series+"Sensor");
			if(null!=sensorSource&&sensorSource.length()>0){
				List<Object> sensorList = new ArrayList<Object>();
				for(String sensor_:sensors.split(",")){
					if(sensorSource.indexOf(sensor_)>-1){
//						Map<String,String> sensorMap = new HashMap<String,String>();
//						sensorMap.put("sensor", sensor_);
//						if(sensor.indexOf(sensor_)>-1){
//							sensorMap.put("checked", "true");
//						}
						sensorList.add(sensor_);
					}
				}
				map.put("sensor", sensorList);
			}else{
				map.put("status", false);
				map.put("msg", "sensor is null");
				return JsonUtils.objectToJson(map);
			}
			
//			从redis中获取级别
			String levels = jedisClient.get(sensor);
			if(null!=levels&&levels.length()>0){
				List<Object> levelList = new ArrayList<Object>();
				for(String level:levels.split(",")){
					if(null!=levelSource&&levelSource.length()>0){
						if(levelSource.indexOf(level)>-1){
							Map<String,String> level_map = new HashMap<String,String>();
							level_map.put("level", level);
							if(productLevel.indexOf(level)>-1){
								level_map.put("checked", "true");
							}
							levelList.add(level_map);
						}
					}
				}
				map.put(sensor, levelList);
			}
			if (queryHistory != null) {
				map.put("status", true);
				map.put("data", queryHistory);
				return JsonUtils.objectToJson(map);
			} else {
				map.put("msg", "queryHistory id null");
				map.put("status", false);
				return JsonUtils.objectToJson(map);
			}
		} catch (Exception e) {
			LOGGER.error("Select Query History By Id list has exception, caused by "+e.getMessage(),Constant.SYSTEM);
			map.put("msg", "error");
			map.put("status", false);
			return JsonUtils.objectToJson(map);
		}

	}

}
