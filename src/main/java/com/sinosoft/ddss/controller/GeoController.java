package com.sinosoft.ddss.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sinosoft.ddss.common.base.entity.ExportEntity;
import com.sinosoft.ddss.common.base.entity.RestfulJSON;
import com.sinosoft.ddss.common.base.entity.TotalInfo;
import com.sinosoft.ddss.common.entity.Additional;
import com.sinosoft.ddss.common.entity.Metadata;
import com.sinosoft.ddss.common.entity.ShopInfo;
import com.sinosoft.ddss.common.entity.SysResource;
import com.sinosoft.ddss.common.entity.User;
import com.sinosoft.ddss.common.entity.query.ShopInfoQuery;
import com.sinosoft.ddss.common.util.JsonUtils;
import com.sinosoft.ddss.jedis.JedisClient;
import com.sinosoft.ddss.model.GlobalData;
import com.sinosoft.ddss.model.Place;
import com.sinosoft.ddss.model.QueryByCircle;
import com.sinosoft.ddss.model.QueryByDistrict;
import com.sinosoft.ddss.model.QueryByPloygon;
import com.sinosoft.ddss.model.QueryCollection;
import com.sinosoft.ddss.service.DecryptToken;
import com.sinosoft.ddss.service.GeoService;
import com.sinosoft.ddss.service.ISysConfigService;
import com.sinosoft.ddss.service.OrderMainService;
import com.sinosoft.ddss.service.ShopCarService;
import com.sinosoft.ddss.utils.FtpUtil;

/**
 * 
 * @author sanyo
 * @since 2018年3月17日 下午9:44:19
 * @version 1.0.0
 * @TODO
 */
@RestController
public class GeoController {

	private static Logger LOGGER = LoggerFactory.getLogger(GeoController.class);

	@Autowired
	private GeoService geoService;
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private  ISysConfigService sysConfigService;
	@Autowired
	private ShopCarService shopCarService;
	@Autowired
	private DecryptToken decryptToken;
	@Autowired
	private OrderMainService orderMainService;
	
		/** <pre>getArea(ploygon 面积)   
		 * 创建人：sanyo     
		 * 创建时间：2018年3月26日 上午9:34:32    
		 * 修改人：宫森      
		 * 修改时间：2018年3月26日 上午9:34:32    
		 * 修改备注： 该方法前台未调用
		 * @param ploygon
		 * @return</pre>    
		 */
		 
	@RequestMapping("/get_area")
	public BigDecimal getArea(String ploygon) {
		ploygon = "POLYGON((114.211163 32.030174,114.476917 31.977436,114.411442 31.749178,114.146329 31.801821,114.211163 32.030174))";
		return geoService.getAreaFromPloygon(ploygon);
	}

	
		/** <pre>getDistance(点 半径 面积)   
		 * 创建人：sanyo      
		 * 创建时间：2018年3月26日 上午9:35:02    
		 * 修改人：宫森      
		 * 修改时间：2018年3月26日 上午9:35:02    
		 * 修改备注： 该方法前台未调用
		 * @return</pre>    
		 */
		 
	@RequestMapping("/get_distance")
	public BigDecimal getDistance() {
		String point1Str = "POINT(-71.064544 42.28787)";
		String point2Str = "POINT(-71.1776820268866 42.3903701743239)";
		return geoService.getDistance(point1Str, point2Str);
	}
	
	
		/** <pre>getDistrict(省市县定位)   
		 * 创建人：sanyo      
		 * 创建时间：2018年3月26日 上午9:33:48    
		 * 修改人：宫森      
		 * 修改时间：2018年3月26日 上午9:33:48    
		 * 修改备注： 
		 * @return</pre>    
		 */
		 
	@RequestMapping("/get_district")
	public String getDistrict(String type, Integer id,HttpServletRequest request) {
//		type = "city";
//		id = 220;
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(type)){
			map.put("status", false);
			map.put("msg", "Type is null");
			return JsonUtils.objectToJson(map);
		}
		if(null == id){
			map.put("status", false);
			map.put("msg", "Gid is null");
			return JsonUtils.objectToJson(map);
		}
		String districtPloygon = geoService.getDistrictPloygon(type, id);
		districtPloygon = districtPloygon.replace("),(", ")),((");
		districtPloygon = districtPloygon.replace("))),(((", ")),((");
		map.put("data", districtPloygon);
		return JsonUtils.objectToJson(map);
	}
	
	
		/** <pre>getPlace(省市县联动)   
		 * 创建人：宫森      
		 * 创建时间：2018年3月26日 下午2:13:53    
		 * 修改人：宫森      
		 * 修改时间：2018年3月26日 下午2:13:53    
		 * 修改备注： 
		 * @param type
		 * @param id
		 * @param code
		 * @return</pre>    
		 */
		 
	@RequestMapping("/get_place")
	public String getPlace(String type, Integer id, String code, String letter) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//先获取省
			List<Place> listPlace = geoService.getPlaceByCode(type, id, code, letter);
			//然后遍历获取
			map.put("data", listPlace);
			map.put("status", true);
		} catch (Exception e) {
			map.put("status", false);
			e.printStackTrace();
		}
		return JsonUtils.objectToJson(map);
	}
	
	/**
	 * 根据字母查询地区
	 * @param type
	 * @param id
	 * @param code
	 * @param letter
	 * @return
	 */
	@RequestMapping("/get_place_by_letter")
	public String getPlaceByLetter(String type, Integer id, String code, String letter) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<List<Map<String, Object>>> jsonList = new ArrayList<>();
		String[] letterArr = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		//先获取省
		List<Place> listPlace = geoService.getPlaceByCode("province", null, "", "letter");
		List<Place> city = geoService.getPlaceByCode("city", null, "", "letter");
		for (String is : letterArr) {
			List<Place> list = new ArrayList<>();
			for (int i = 0; i < listPlace.size(); i++) {
				String s2 = listPlace.get(i).getNameeng().substring(0, 1);
				if(is.equals(s2)){
					Place place = listPlace.get(i);
					place.setInitials(is);
					list.add(place);
				}
			}
			List<Map<String, Object>> lists = new ArrayList<>();
			Map<String,List<Map<String, Object>>> letterMap = new LinkedHashMap<String, List<Map<String, Object>>>();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> model = new LinkedHashMap<String, Object>();
				Set<Place> set = new HashSet<Place>();
				String s1 = list.get(i).getCode().substring(0, 2);
				for (int j = 0; j < city.size(); j++) {
					String s2 = city.get(j).getCode().substring(0, 2);
					if(s1.equals(s2)){
						set.add(city.get(j));
					}
				}
				model.put("province",list.get(i));
				model.put("city", set);
				lists.add(model);
			}
			letterMap.put(is, lists);
			jsonList.add(lists);
		}
		/*for (int i = 0; i < listPlace.size(); i++) {
			Map<String, Object> model = new LinkedHashMap<String, Object>();
			Set<Place> li = new HashSet<Place>();
			String s1 = listPlace.get(i).getCode().substring(0, 2);
			for (int j = 0; j < city.size(); j++) {
				String s2 = city.get(j).getCode().substring(0, 2);
				if(s1.equals(s2)){
					li.add(city.get(j));
				}
			}
			model.put("province",listPlace.get(i));
			model.put("city", li);
			jsonList.add(model);
		}*/
		//然后遍历获取
		map.put("data", jsonList);
		return JsonUtils.objectToJson(map);
	}
		/** <pre>queryByPloygon(数据查询)   
		 * 创建人：sanyo      
		 * 创建时间：2018年3月22日 下午6:01:18    
		 * 修改人：宫森      
		 * 修改时间：2018年3月22日 下午6:01:18    
		 * 修改备注： 该方法前台未调用
		 * @param request
		 * @return</pre>    
		 */
		 
	@RequestMapping("/query_by_ploygon")
	public String queryByPloygon(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//01 getting page
			String page = request.getParameter("page");
			//02 getting pageSize
			String pageSize = request.getParameter("pageSize");
			//03 getting sensor
			String sensor = request.getParameter("sensor");
			//04 getting ploygon
			String ploygon = request.getParameter("ploygon");
			//05 getting startTime
			String startTime = request.getParameter("startTime");
			//06 getting endTime
			String endTime = request.getParameter("endTime");
			//07 getting satellite
			String satellite = request.getParameter("satellite");
			//08 getting productLevel
			String productLevel = request.getParameter("productLevel");
			//09 getting cloudCoverage
			String cloudCoverage = request.getParameter("cloudCoverage");
			//10 getting sortBy 排序SQL
			String sortBy = request.getParameter("sortBy");
			//11 getting sortType // 1升序 2降序
			String sortType = request.getParameter("sortType");
			QueryByPloygon queryModel = new QueryByPloygon();
			//01 setting page
			if(!StringUtils.isBlank(page)){
				queryModel.setPage(Integer.valueOf(page));
			}
			//02 setting pageSize
			if(!StringUtils.isBlank(pageSize)){
				queryModel.setPageSize(Integer.valueOf(pageSize));
			}
			//03 setting ploygon
			ploygon = "POLYGON((114.211163 32.030174,114.476917 31.977436,114.411442 31.749178,114.146329 31.801821,114.211163 32.030174))";
			if(!StringUtils.isBlank(ploygon)){
				queryModel.setPloygon(ploygon);
			}
			//04 setting sensor
			if(!StringUtils.isBlank(sensor)){
				queryModel.setSensor(sensor);
			}
			//05 setting startTime
			if(!StringUtils.isBlank(startTime)){
				queryModel.setStartTime(startTime);
			}
			//06 setting endTime
			if(!StringUtils.isBlank(endTime)){
				queryModel.setEndTime(endTime);
			}
			//07 setting satellite
			if(!StringUtils.isBlank(satellite)){
				queryModel.setSatellite(satellite);
			}
			//08 setting productLevel
			if(!StringUtils.isBlank(productLevel)){
				queryModel.setProductLevel(productLevel);
			}
			//09 setting cloudCoverage
			if(!StringUtils.isBlank(cloudCoverage)){
				queryModel.setCloudCoverage(Integer.valueOf(cloudCoverage));
			}
			//10 setting sortBy 排序SQL
			if(!StringUtils.isBlank(sortBy)){
				queryModel.setSortBy(sortBy);
			}
			//11 setting sortType // 1升序 2降序
			if(!StringUtils.isBlank(sortType)){
				queryModel.setSortType(sortType);
			}
			
			List<Metadata> queryMetadata = geoService.queryMetadata(queryModel);
			Integer metaDateCount = geoService.getMetaDateCount(queryModel);
			TotalInfo totalInfo = new TotalInfo(metaDateCount, queryModel.getPageSize(), queryModel.getPage(),
					queryModel.getStartNum());
			map.put("data", queryMetadata);
			map.put("totalInfo", totalInfo);
			map.put("status", true);
			map.put("msg", "数据列表查询成功");
			LOGGER.info("数据列表查询成功");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", false);
			map.put("msg", "数据列表查询异常");
			LOGGER.info("数据列表查询异常");
		}
		return JsonUtils.objectToJson(map);
	}
	
	public String queryByRound(HttpServletRequest request){
		return JsonUtils.objectToJson("");
	}

	
		/** <pre>getGlobalByPloygon(根据ploygon获得覆盖比)   
		 * 创建人：sanyo      
		 * 创建时间：2018年3月26日 上午9:30:42    
		 * 修改人：宫森      
		 * 修改时间：2018年3月26日 上午9:30:42    
		 * 修改备注： 该方法前台未调用
		 * @param request
		 * @return</pre>    
		 */
		 
	@RequestMapping("/get_global_by_ploygon")
	public GlobalData getGlobalByPloygon(String orderMainId, HttpServletRequest request) {
		//01 getting page
		String page = request.getParameter("page");
		//02 getting pageSize
		String pageSize = request.getParameter("pageSize");
		//03 getting sensor
		String sensor = request.getParameter("sensor");
		//04 getting ploygon
		String ploygon = request.getParameter("ploygon");
		ploygon = "POLYGON((114.211163 32.030174,114.476917 31.977436,114.411442 31.749178,114.146329 31.801821,114.211163 32.030174))";
		if(StringUtils.isBlank(orderMainId)){
		}
		//根据采集单的数据id查出来采集的条件 然后传入这个对象 到 geo
		String userJson = restTemplate.getForObject(
				"http://192.168.200.61:8179/order/selectOrderMainById?orderMainId=" + orderMainId, String.class);
		QueryByPloygon queryModel = new QueryByPloygon();
		//01 setting page
		if(!StringUtils.isBlank(page)){
			queryModel.setPage(Integer.valueOf(page));
		}
		//02 setting pageSize
		if(!StringUtils.isBlank(pageSize)){
			queryModel.setPageSize(Integer.valueOf(pageSize));
		}
		//03 setting ploygon
		if(!StringUtils.isBlank(ploygon)){
			queryModel.setPloygon(ploygon);
		}
		//04 setting sensor
		if(!StringUtils.isBlank(sensor)){
			queryModel.setSensor(sensor);
		}
		return geoService.getGlobalData(queryModel);
	}

	
		/** <pre>queryByCircle(圆 数据查询)   
		 * 创建人：sanyo      
		 * 创建时间：2018年3月26日 上午9:31:27    
		 * 修改人：宫森      
		 * 修改时间：2018年3月26日 上午9:31:27    
		 * 修改备注： 该方法前台未调用
		 * @return</pre>    
		 */
		 
	@RequestMapping("/query_by_circle")
	public String queryByCircle(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		//01 getting page
		String ploygon = request.getParameter("ploygon");
		//02 getting page
		String radius = request.getParameter("radius");
		//03 getting page
		String sensor = request.getParameter("sensor");
		//04 getting page
		String page = request.getParameter("page");
		//05 getting pageSize
		String pageSize = request.getParameter("pageSize");
		//06 getting startTime
		String startTime = request.getParameter("startTime");
		//07 getting endTime
		String endTime = request.getParameter("endTime");
		//08 getting satellite
		String satellite = request.getParameter("satellite");
		//09 getting productLevel
		String productLevel = request.getParameter("productLevel");
		//10 getting cloudCoverage
		String cloudCoverage = request.getParameter("cloudCoverage");
		//11 getting sortBy 排序SQL
		String sortBy = request.getParameter("sortBy");
		//12 getting sortType // 1升序 2降序
		String sortType = request.getParameter("sortType");
		QueryByCircle queryModel = new QueryByCircle();
		
		//01 setting page
		ploygon = "POINT(114.428694 32.789413)";
		if(!StringUtils.isBlank(ploygon)){
			queryModel.setPointPloygon(ploygon);
		}
		//02 setting page
		radius="100";
		if(!StringUtils.isBlank(radius)){
			queryModel.setRadius(new BigDecimal(radius));
		}
		//03 setting page
		sensor="HY-1C";
		if(!StringUtils.isBlank(sensor)){
			queryModel.setSensor(sensor);
		}
		
		//04 setting page
		if(!StringUtils.isBlank(page)){
			queryModel.setPage(Integer.valueOf(page));
		}
		//05 setting pageSize
		if(!StringUtils.isBlank(pageSize)){
			queryModel.setPageSize(Integer.valueOf(pageSize));
		}
		//06 setting startTime
		if(!StringUtils.isBlank(startTime)){
			queryModel.setStartTime(startTime);
		}
		//07 setting endTime
		if(!StringUtils.isBlank(endTime)){
			queryModel.setEndTime(endTime);
		}
		//08 setting satellite
		if(!StringUtils.isBlank(satellite)){
			queryModel.setSatellite(satellite);
		}
		//09 setting productLevel
		if(!StringUtils.isBlank(productLevel)){
			queryModel.setProductLevel(productLevel);
		}
		//10 setting cloudCoverage
		if(!StringUtils.isBlank(cloudCoverage)){
			queryModel.setCloudCoverage(Integer.valueOf(cloudCoverage));
		}
		//11 setting sortBy 排序SQL
		if(!StringUtils.isBlank(sortBy)){
			queryModel.setSortBy(sortBy);
		}
		//12 setting sortType // 1升序 2降序
		if(!StringUtils.isBlank(sortType)){
			queryModel.setSortType(sortType);
		}
		List<Metadata> queryMetadata = geoService.queryMetadata(queryModel);
		Integer totalInfo = geoService.queryFromCircleCount(queryModel);
		map.put("data", queryMetadata);
		map.put("totalInfo", totalInfo);
		map.put("status", true);
		map.put("msg", "数据列表查询成功");
		LOGGER.info("数据列表查询成功");
		return JsonUtils.objectToJson(map);
		//return geoService.queryMetadata(queryModel);
	}

	
		/** <pre>getGlobalDataByCircle(圆 覆盖比)   
		 * 创建人：sanyo     
		 * 创建时间：2018年3月26日 上午9:32:08    
		 * 修改人：宫森      
		 * 修改时间：2018年3月26日 上午9:32:08    
		 * 修改备注： 该方法前台未调用
		 * @return</pre>    
		 */
		 
	@RequestMapping("/get_global_by_circle")
	public GlobalData getGlobalDataByCircle() {
		String ploygon = "POINT(114.428694 32.789413)";
		QueryByCircle queryModel = new QueryByCircle();
		queryModel.setPointPloygon(ploygon);
		queryModel.setRadius(new BigDecimal(100));
		queryModel.setSensor("HY-1C");
		return geoService.getGlobalData(queryModel);
	}

	
		/** <pre>queryByDistrict(省市县 数据查询)   
		 * 创建人：sanyo     
		 * 创建时间：2018年3月26日 上午9:32:53    
		 * 修改人：宫森      
		 * 修改时间：2018年3月26日 上午9:32:53    
		 * 修改备注： 
		 * @param sensor
		 * @param id
		 * @param type
		 * @return</pre>    
		 */
		 
	@RequestMapping("/query_by_district")
	public String queryByDistrict(String sensor, Integer id, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		QueryByDistrict queryModel = new QueryByDistrict();
//		sensor="HY-1C";
//		id=2;
//		type="city";
		queryModel.setSensor(sensor);
		queryModel.setDistrictId(id);
		queryModel.setDistrictType(type);
		List<Metadata> queryMetadata = geoService.queryMetadata(queryModel);
		//Integer totalInfo = geoService.queryFromCircleCount(queryModel);
		map.put("data", queryMetadata);
		//map.put("totalInfo", totalInfo);
		map.put("status", true);
		map.put("msg", "数据列表查询成功");
		LOGGER.info("数据列表查询成功");
		return JsonUtils.objectToJson(map);
		//return geoService.queryMetadata(queryModel);
	}

	
		/** <pre>getGlobalByDistrict(省市县 覆盖比)   
		 * 创建人：sanyo      
		 * 创建时间：2018年3月26日 上午9:33:13    
		 * 修改人：宫森      
		 * 修改时间：2018年3月26日 上午9:33:13    
		 * 修改备注： 该方法前台未调用
		 * @param sensor
		 * @param id
		 * @param type
		 * @return</pre>    
		 */
		 
	@RequestMapping("/get_global_by_district")
	public GlobalData getGlobalByDistrict(String sensor, Integer id, String type) {
		QueryByDistrict queryModel = new QueryByDistrict();
		queryModel.setSensor(sensor);
		queryModel.setDistrictId(id);
		queryModel.setDistrictType(type);

		// queryModel.setSensor("HY-1C");
		// queryModel.setDistrictId(1);
		// queryModel.setDistrictType("world");

		// queryModel.setDistrictId(220);
		// queryModel.setDistrictType("city");
		//
		// queryModel.setDistrictId(16);
		// queryModel.setDistrictType("proince");

		return geoService.getGlobalData(queryModel);
	}
	
	/** <pre>mapQuery(地图查询)
	 * 创建人：杨金地    
	 * 创建时间：2018年4月10日 上午11:37:13    
	 */
	@RequestMapping("/oauth/map_query")
	public String mapQuery(@RequestBody QueryCollection queryModel, HttpServletRequest request){
//		@RequestMapping("/oauth/map_query")
//		public String mapQuery( QueryCollection queryModel, HttpServletRequest request){
//		queryModel.setTimeType(null);
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取卫星传感器字符串	HY-2B:RA,RAD#HY-2C:SCA,RC
		String satellateRes = "";
		String satellites = queryModel.getSatellite();
		if(!StringUtils.isBlank(satellites)){
			String[] satesens = satellites.split("!");
			for (String satesen : satesens) {
				String[] sate = satesen.split(":");
				String satellite = "";
				String sensors = "";
				satellite = sate[0];
				if(sate.length > 1){
					sensors = sate[1];
					String[] sensorss = sensors.split(",");
					for (String sensor : sensorss) {
						satellateRes += " (dm.satellite = '" + satellite + "' AND dm.sensor = '" + sensor + "') OR";
					}
				}else{
					satellateRes += " (dm.satellite = '" + satellite + "') OR";
				}
			}
		}
		if(!satellateRes.equals("")){
			satellateRes = satellateRes.substring(0, satellateRes.length()-3);
			satellateRes = " AND (" + satellateRes +")";
			queryModel.setSatellite(satellateRes);
		}
		String type = queryModel.getWorkType();
		Short orderType = 2;
		if(null!=type&&"2".equals(type)){
			
			String productLevel = queryModel.getProductLevel();
			
			productLevel = getProductionLevel(productLevel);
			queryModel.setProductLevel(productLevel);
			orderType = 1;
		}
		if(!StringUtils.isBlank(queryModel.getProductLevel())){
			queryModel.setProductLevel("LEVE"+queryModel.getProductLevel());
		}
		// 质量等级
		String qualityGrade = jedisClient.get("qualityGrade");
		queryModel.setQualityGrade(qualityGrade);
		String aoi = request.getParameter("aoi");
		queryModel.setPloygon(aoi);
		List<Metadata> queryMetadata = geoService.mapQuery(queryModel);
		// 图片服务器IP跟端口
		String imageIp = jedisClient.get("imageIp");
		String imagePort = jedisClient.get("imagePort");
		// 过滤购物车已经存在的数据设置为已经添加到购物车属性
		if(queryMetadata.size()>0){
			StringBuffer dataIds = new StringBuffer();
			for (Metadata metadata : queryMetadata) {
				// 给图片添加图片服务器路径
				if(!StringUtils.isBlank(metadata.getQuickFileUrl())){
					metadata.setQuickFileUrl(imageIp + ":" + imagePort + "/" + metadata.getQuickFileUrl()); 
				}
				if(!StringUtils.isBlank(metadata.getThumbFileUrl())){
					metadata.setThumbFileUrl(imageIp + ":" + imagePort + "/" + metadata.getThumbFileUrl());
				}
				Long dataId = metadata.getDataId();
				dataIds.append(dataId + ",");
			}
			String dataIdsS = dataIds.toString();
			if(!dataIdsS.equals("")){
				dataIdsS = dataIdsS.substring(0,dataIdsS.length()-1);
			}
			ShopInfoQuery shopInfoQuery = new ShopInfoQuery();
			shopInfoQuery.setDataIds(dataIdsS);
			shopInfoQuery.setOrderType(orderType);
			if(orderType == 1){
				shopInfoQuery.setOutProductLevel(queryModel.getProductLevel());
			}
			User user = decryptToken.decyptToken(queryModel.getToken());
			shopInfoQuery.setUserName(user.getUserName());
			shopInfoQuery.setPageSize(null);
			List<ShopInfo> shopInfoList = this.shopCarService.ListShopInfo(shopInfoQuery);
//			List<ShopInfo> shopInfoList = shopCarService.selectByDataIds(dataIdsS);
			for (ShopInfo shopInfo : shopInfoList) {
				for (Metadata metadata : queryMetadata) {
					if(shopInfo.getDataId().longValue() == metadata.getDataId().longValue()){
						metadata.setShopCar(true);
						continue;
					}
				}
			}
		}
		Integer allcount = geoService.mapQueryCount(queryModel);
		Map<String, String> countSum = geoService.mapQueryCountSum(queryModel);
		TotalInfo totalInfo = new TotalInfo(allcount, queryModel.getPageSize(), queryModel.getPage(),
				queryModel.getStartNum());
		map.put("data", queryMetadata);
		map.put("totalInfo", totalInfo);
		map.put("sumSize", countSum.get("sum"));
		map.put("status", true);
		map.put("msg", "数据列表查询成功");
		LOGGER.info("数据列表查询成功");
		return JsonUtils.objectToJson(map);
	}
	
	
	
	/** <pre>mapQuery(地图查询传感器单选)
	 * 创建人：josen 
	 * 创建时间：2018年4月10日 上午11:37:13    
	 */
	@RequestMapping("/oauth/map_query_sensor")
	public String mapQuerySensor(@RequestBody QueryCollection queryModel, HttpServletRequest request){
//		public String mapQuerySensor( QueryCollection queryModel, HttpServletRequest request){
		
		
		LOGGER.error("==============================================================================接到请求时间："+System.currentTimeMillis());
		
		Map<String, Object> map = new HashMap<String, Object>();
		LOGGER.debug("用户信息："+queryModel.getToken());
		LOGGER.debug(queryModel.toString());
		User user = decryptToken.decyptToken(queryModel.getToken());
		if(null==user){
			map.put("status", false);
			map.put("msg", "user is null");
			LOGGER.info("user is null");
			return JsonUtils.objectToJson(map);
		}
		String roleId = String.valueOf(user.getRoleId());
//		如果用户是超级管理员就要无视存储状态
//		因数据问题，暂时将空间错误的信息更改了存储状态，管理员暂时不可查询全部数据
//		if("2".equals(roleId)){
//			queryModel.setIsDelete(-1);
//		}
//		如果这个角色有敏感数据权限，那就可以查询敏感数据
		List<SysResource> sysResource_list = user.getSysSource();
		for(SysResource sysResource:sysResource_list){
			if(sysResource.getUrl().equals("Sensitive")){
				queryModel.setIsSensitive(1);
				break;
			}
		}
		
//		String type = queryModel.getWorkType();
		String productLevels = queryModel.getProductLevel();
		String satellites = queryModel.getSatellite();
		String sensors = queryModel.getSensor();
//		给将卫星改为'卫星','卫星'格式方便查询
		if(!StringUtils.isBlank(satellites)){
			String sates = new String();
			for(String satellite:satellites.split(",")){
				sates+="'"+satellite+"',";
			}
			satellites = sates.substring(0, sates.length()-1);
			queryModel.setSatellite(satellites);
		}
//		传感器多选处理
//		给将传感器改为'传感器','传感器'格式方便查询
		if(!StringUtils.isBlank(sensors)){
			String sens = new String();
			for(String sensor:sensors.split(",")){
//				将传感器的系列去掉
				String[] sensores = sensor.split("-");
				sensor = sensores[sensores.length-1];
				sens+="'"+sensor+"',";
			}
			sensors = sens.substring(0, sens.length()-1);
			queryModel.setSensor(sensors);
		}

		
		
		
//		海动ALT级别特殊处理
		if(!StringUtils.isBlank(productLevels)){
			String levels = new String();
			for(String productLevel : productLevels.split(",")){
				if(productLevel.equals("ODR_L2N")){
					levels+="'ODR_LEVEL2N',";
				}else if(productLevel.equals("IDR_L2M")){
					levels+="'IDR_LEVEL2M',";
				}else if(productLevel.equals("GDR_L2P")){
					levels+="'GDR_LEVEL2P',";
				}else if(productLevel.equals("SDR_L2M")){
					levels+="'SDR_LEVEL2M',";
				}else{
					levels+="'LEVE"+productLevel+"',";
				}
			}
			productLevels = levels.substring(0, levels.length()-1);
			queryModel.setProductLevel(productLevels);
		}
		
		String sql = "";
		String satellites1 = "";
		String sensor = "";
		String level = "";
		
//		获取无空间数据
		String noRegion = jedisClient.get("noRegion");
		noRegion = noRegion.substring(1, noRegion.length()-1);
		noRegion = noRegion.replaceAll("\\\\", "");
//		将无空间数据字符串转义
		List<Map<String, String>> noRegion_list = new ArrayList<Map<String,String>>();
		noRegion_list = JsonUtils.jsonToPojo(noRegion, noRegion_list.getClass());
//		获取满足无空间数据的查询条件
		for(Map<String, String> noRegion_map:noRegion_list){
			String noRegion_value = noRegion_map.get("value");
			String[] noRegion_values = noRegion_value.split(":");
			String satellite_ = noRegion_values[0];
			String sensor_ = noRegion_values[1];
			String level_ = noRegion_values[2];
			
			if(queryModel.getSatellite()!=null&&queryModel.getSatellite().indexOf(satellite_)>-1){
				if(queryModel.getSensor().indexOf(sensor_)>-1){
					if(queryModel.getProductLevel().indexOf(level_)>-1){
//						satellites1+="'"+satellite_+"',";
//						sensor+="'"+sensor_+"',";
//						level+="'"+level_+"',";
						
						sql += " or (dm.satellite = '"+satellite_+"' and dm.sensor = '"+sensor_+"' and dm.product_level = '"+level_+"')";
					}
				}
			}
			
		}
		
		if(!sql.equals("")){
			queryModel.setSql(sql);
			map.put("msgdata", "查询条件中包含无空间数据，无空间数据结果已查询");
		}
		
//		拼接查询条件
//		if(!satellites1.equals("")&&!sensor.equals("")&&!level.equals("")){
//			satellites1 = satellites1.substring(0, satellites1.length()-1);
//			sensor = sensor.substring(0, sensor.length()-1);
//			level = level.substring(0, level.length()-1);
//			sql += "or (dm.satellite in ("+satellites1+") and dm.sensor in ("+sensor+") and dm.product_level in ("+level+"))";
//			queryModel.setSql(sql);
//			map.put("msgdata", "查询条件中包含无空间数据，无空间数据结果已查询");
//		}
		
		
		
		
		
		//判断prductName不为空的时候
		if(!StringUtils.isBlank(queryModel.getProductName())){
			String productNames = queryModel.getProductName();
			String productName ="";
			for(String productName_ : productNames.split(",")){
				productName+="'"+productName_+"',";
			}
			productName = productName.substring(0, productName.length()-1);
			queryModel.setProductName(productName);
		}
		
//		将轨道号进行处理，用逗号（,）分割
		String orbitIds = queryModel.getOrbitId();
		if(!StringUtils.isBlank(orbitIds)){
			String orbitId = new String();
			for(String orbitId_:orbitIds.split(",")){
				orbitId+="'"+orbitId_+"',";
			}
			orbitId = orbitId.substring(0, orbitId.length()-1);
			queryModel.setOrbitId(orbitId);
		}
		
		if(queryModel.getStartOrbitCycle()!=null&&queryModel.getStartOrbitPass()!=null){
			
//		获取开始cycle和pass
			int startCycle = queryModel.getStartOrbitCycle();
			int startPass = queryModel.getStartOrbitPass();
//		获取结束cycle和pass
			int endCycle = queryModel.getEndOrbitCycle();
			int endPass = queryModel.getEndOrbitPass();
//		如果cycle和pass都大于0再做处理
			if(startCycle>0&&startPass>0&&endCycle>0&&endPass>0){
				if(endCycle-startCycle==0){
//			如果开始cycle和结束cycle相同，就查cycle==startCycle与startpass<=pass<=endPass
					queryModel.setCycleDifference(0);
				}else if(endCycle-startCycle==1){
//			如果开始cycle和结束cycle相差1，就查startCycle<=cycle与startpass<=pass 或 endCycle<=cycle与endpass<=pass
					queryModel.setCycleDifference(1);
				}else if(endCycle-startCycle>1){
//			如果开始cycle和结束cycle相差大于1，就查startCycle==cycle与startpass<=pass 或 endCycle==cycle与endpass<=pass 或 	startpass<pass<endPass
					queryModel.setCycleDifference(2);
				}
				
			}
		}else{
			queryModel.setCycleDifference(-1);
		}
//		获取成像模式
		String imagingMode = queryModel.getImagingMode();
		LOGGER.info("imagingMode====="+imagingMode);
		System.out.println(imagingMode);
		if(!StringUtils.isBlank(imagingMode)){
			
			String imaging_mode = "";
//			显示的成像模式不同于元数据中的成像模式
			String[] imagingMode_list =	imagingMode.split(",");
			if(imagingMode_list.length>1){
				String imagingModes = "";
				for(String imagingMode_:imagingMode_list){
					imagingMode_ = jedisClient.get(imagingMode_+"_MODEL");
					imagingModes+="'"+imagingMode_+"',";
				}
				imaging_mode = imagingModes.substring(0, imagingModes.length()-1);
			}else{
//				成像模式放在redis中，“成像模式__MODEL”查询成像模式完整名称
				imaging_mode = jedisClient.get(imagingMode+"_MODEL");
				if(imaging_mode==null||imaging_mode.equals("")){
					map.put("status", false);
					map.put("msg", "imagingMode is not exist");
					LOGGER.info("imagingMode is not exist");
					return JsonUtils.objectToJson(map);
				}
				imaging_mode = "'"+imaging_mode+"'";
			}
			
			queryModel.setImagingMode(imaging_mode);
		}
		
		// 质量等级
		String qualityGrade = jedisClient.get("qualityGrade");
		queryModel.setQualityGrade(qualityGrade);
		
		long start = System.currentTimeMillis();
		LOGGER.error("========================================================================查询数据开始时间："+start);
		
		
		
		List<Metadata> queryMetadata = new ArrayList<Metadata>();
		try {
			queryMetadata = geoService.mapQuerySensor(queryModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		long end = System.currentTimeMillis();
		LOGGER.error("==========================================================================查询数据结束时间："+end);
		LOGGER.error("==========================================================================查询数据消耗时间："+(end-start));
		// 图片服务器IP跟端口
		String imageIp = jedisClient.get("imageIp");
		String imagePort = jedisClient.get("imagePort");
		// 过滤购物车已经存在的数据设置为已经添加到购物车属性
		if(null!=queryMetadata&&queryMetadata.size()>0){
			StringBuffer dataIds = new StringBuffer();
			for (Metadata metadata : queryMetadata) {
				
				String satellite = metadata.getSatellite();
//				暂时只展示1号卫星的区域
				if(!satellite.equals("HY-1C")&&!satellite.equals("HY-1D")&&!(satellite.indexOf("GF")>-1)){
					metadata.setGeom("");
				}
				
				// 给图片添加图片服务器路径
				if(!StringUtils.isBlank(metadata.getQuickFileUrl())){
					metadata.setQuickFileUrl(imageIp + ":" + imagePort + "/" + metadata.getQuickFileUrl()); 
				}
				if(!StringUtils.isBlank(metadata.getThumbFileUrl())){
					metadata.setThumbFileUrl(imageIp + ":" + imagePort + "/" + metadata.getThumbFileUrl());
				}
				String geom = String.valueOf(metadata.getGeom());
				if(geom.indexOf("GEOMETRYCOLLECTION")>-1){
					geom = geom.replace("GEOMETRYCOLLECTION(", "");
					geom = geom.substring(0, geom.length()-1);
				}
				metadata.setGeom(geom);
				
				Long dataId = metadata.getDataId();
				dataIds.append(dataId + ",");
			}
			String dataIdsS = dataIds.toString();
			if(!dataIdsS.equals("")){
				dataIdsS = dataIdsS.substring(0,dataIdsS.length()-1);
			}
			ShopInfoQuery shopInfoQuery = new ShopInfoQuery();
			shopInfoQuery.setDataIds(dataIdsS);
		
			shopInfoQuery.setUserName(user.getUserName());
			shopInfoQuery.setPageSize(queryModel.getPageSize());
//			获取数据在购物车中对应的数据，已加入购物车的数据不可再次加入购物车
			List<ShopInfo> shopInfoList = this.shopCarService.ListShopInfo(shopInfoQuery);
			for (ShopInfo shopInfo : shopInfoList) {
				for (Metadata metadata : queryMetadata) {
					if(shopInfo.getDataId().longValue() == metadata.getDataId().longValue()){
						metadata.setShopCar(true);
						continue;
					}
				}
			}
		}
		LOGGER.error("===========================================================================循环处理结束时间："+System.currentTimeMillis());
		if(queryModel.getPage()!=null&&queryModel.getPage()==1){
			
			Integer allcount = geoService.mapQueryCountSensor(queryModel);
			Map<String, String> countSum = geoService.mapQueryCountSumSensor(queryModel);
			TotalInfo totalInfo = new TotalInfo(allcount, queryModel.getPageSize(), queryModel.getPage(),
					queryModel.getStartNum());
			map.put("totalInfo", totalInfo);
			map.put("sumSize", countSum.get("sum"));
		}
		LOGGER.error("==============================================================================分页计算结束时间："+System.currentTimeMillis());
		
		map.put("data", queryMetadata);
		map.put("status", true);
		map.put("msg", "数据列表查询成功");
		LOGGER.info("数据列表查询成功");
		return JsonUtils.objectToJson(map);
	}
	

	
	@RequestMapping("/oauth/updateIsDeleteById")
	public String updateIsDeleteById( String ids,String isDelete){
		Map<String, Object> rmap = new HashMap<String, Object>();
//		如果ids是空就不处理
		if(null==ids){
			rmap.put("status", false);
			rmap.put("msg", "ids is null");
			LOGGER.info("ids is null");
			return JsonUtils.objectToJson(rmap);
		}
		if(null==isDelete){
			rmap.put("status", false);
			rmap.put("msg", "isDelete is null");
			LOGGER.info("isDelete is null");
			return JsonUtils.objectToJson(rmap);
		}
		Map<String, Object> map = new HashMap<String, Object>();
//		如果有逗号就加上‘’ 拼接成'','' 的格式
		if(ids.indexOf(",")>-1){
			String idList = new String();
			String[] id_list = ids.split(",");
			for(String id:id_list){
				idList+=("'"+id+"',");
			}
//			去掉最后一个逗号
			ids = idList.substring(0, idList.length()-1);
			map.put("ids", ids);
		}else{
//			如果没有逗号就是一条
			map.put("ids", "'"+ids+"'");
		}
//		将数据的删除状态改为 0逻辑删除，1物理删除，2正常
		map.put("isDelete", Integer.parseInt(isDelete));
		int i = geoService.updateIsDeleteById(map);
//	如果修改成功就返回true
		if(i>0){
			rmap.put("status", true);
		}else{
			rmap.put("status", false);
		}
		return JsonUtils.objectToJson(rmap);
	}
	
	/** <pre>mapQuery_satelliteSensorProductLevel(地图查询并返回所包含的卫星、传感器、级别)
	 * 创建人：杨金地    
	 * 创建时间：2018年4月10日 上午11:37:13    
	 */
	@RequestMapping("/oauth/mapQuery_satelliteSensorProductLevel")
	public String mapQuery_satelliteSensorProductLevel(@RequestBody QueryCollection queryModel, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Metadata> queryMetadata = geoService.mapQuery(queryModel);
		Integer allcount = geoService.mapQueryCount(queryModel);
		TotalInfo totalInfo = new TotalInfo(allcount, queryModel.getPageSize(), queryModel.getPage(),
				queryModel.getStartNum());
		
		//1、卫星	2、传感器	3、级别
		queryModel.setGroupType("satellite");
		List<String> satelliteList= geoService.GpoupSatelliteSensorProductLevel(queryModel);
		queryModel.setGroupType("sensor");
		List<String> sensorList= geoService.GpoupSatelliteSensorProductLevel(queryModel);
		queryModel.setGroupType("product_level");
		List<String> productLevelList= geoService.GpoupSatelliteSensorProductLevel(queryModel);
		
		map.put("data", queryMetadata);
		map.put("satelliteList", satelliteList);
		map.put("sensorList", sensorList);
		map.put("productLevelList", productLevelList);
		map.put("totalInfo", totalInfo);
		map.put("status", true);
		map.put("msg", "数据列表查询成功");
		LOGGER.info("数据列表查询成功");
		return JsonUtils.objectToJson(map);
	}
	
	
	
	/** <pre>mapQuery(地图查询)
	 * 创建人：杨金地    
	 * 创建时间：2018年4月10日 上午11:37:13    
	 */
	@RequestMapping("/oauth/get_by_id")
	public String GetById(String data_id){
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(data_id)){
			map.put("msg", "data_id null");
			map.put("status", false);
			return JsonUtils.objectToJson(map);
		}
		String[] split = data_id.split(",");
		List<Integer> lists = new ArrayList<Integer>();
		for (String id : split) {
			if(!StringUtils.isBlank(id)){
				lists.add(Integer.parseInt(id));
			}
		}
		List<Metadata> metadata = geoService.GetByDataId(lists);
		map.put("data", metadata);
		
		map.put("status", true);
		map.put("msg", "查询详情成功");
		LOGGER.info("查询详情成功");
		return JsonUtils.objectToJson(map);
	}

	
		/** <pre>get_global(覆盖显示)
		 * Author：sen_kung     
		 * Create date：2018年5月5日 下午2:32:31    
		 * Author：sen_kung      
		 * Update date：2018年5月5日 下午2:32:31    
		 * Description： 
		 * @param orderMainId
		 * @param request
		 * @return</pre>    
		 */
		 
	@RequestMapping(value = "/get_global", method = RequestMethod.POST)
	public RestfulJSON get_global(String orderMainId, HttpServletRequest request) {
		RestfulJSON json = new RestfulJSON();
		if(StringUtils.isBlank(orderMainId)){
			json.setMsg("Order main id is null");
			json.setStatus(false);
			return json;
		}
/*		//根据采集单的数据id查出来采集的条件 然后传入这个对象 到 geo
		String isbean = restTemplate.getForObject(
				"http://10.13.121.135:8769/oauth/order/selectOrderMainById?orderMainId=" + orderMainId, String.class);
		if(StringUtils.isBlank(isbean)){
			json.setMsg("No data");
			json.setStatus(false);
		}*/
//		Additional bean = FastJsonUtil.toBean(isbean, Additional.class);
		try {
			Additional bean = this.orderMainService.getOrderMainById(Long.parseLong(orderMainId));
			GlobalData global = geoService.getGlobal(bean);
			json.setStatus(true);
			global.setOrderGeom(bean.getAoi());
			json.setData(global);
		} catch (Exception e) {
			json.setStatus(false);
			e.printStackTrace();
		}
		return json;
	}
	
	
		/** <pre>getPlacesByName(模糊查询名字)   
		 * Author：sen_kung     
		 * Create date：2018年5月15日 上午10:20:44    
		 * Author：sen_kung      
		 * Update date：2018年5月15日 上午10:20:44    
		 * Description： 
		 * @param type
		 * @param cityName
		 * @param request
		 * @return</pre>    
		 */
		 
	@RequestMapping("/get_places_by_name")
	public String getPlacesByName(String type, String cityName,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(type)){
			map.put("status", false);
			map.put("msg", "Type is null");
			return JsonUtils.objectToJson(map);
		}
		if(null == cityName){
			map.put("status", false);
			map.put("msg", "Gid is null");
			return JsonUtils.objectToJson(map);
		}
		QueryCollection collection = new QueryCollection();
		collection.setType(type);
		collection.setCityName(cityName);
		List<Place> listPlace = geoService.getPlacesByName(collection);
		map.put("data", listPlace);
		return JsonUtils.objectToJson(map);
	}
		/** <pre>getDistrictByName(根据名字查询区域)   
		 * Author：sen_kung     
		 * Create date：2018年5月15日 上午9:43:24    
		 * Author：sen_kung      
		 * Update date：2018年5月15日 上午9:43:24    
		 * Description： 
		 * @param type
		 * @param cityName
		 * @param request
		 * @return</pre>    
		 */
		 
	@RequestMapping("/get_district_by_name")
	public String getDistrictByName(String type, String cityName,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(type)){
			map.put("status", false);
			map.put("msg", "Type is null");
			return JsonUtils.objectToJson(map);
		}
		if(null == cityName){
			map.put("status", false);
			map.put("msg", "Gid is null");
			return JsonUtils.objectToJson(map);
		}
		QueryCollection collection = new QueryCollection();
		collection.setType(type);
		collection.setCityName(cityName);
		long start = new Date().getTime();
		System.out.println(start);
		String districtPloygon = geoService.getDistrictByName(collection);
		districtPloygon = districtPloygon.replace("),(", ")),((");
		districtPloygon = districtPloygon.replace("))),(((", ")),((");
		long end = new Date().getTime();
		System.out.println("时间差"+(end-start));
		map.put("data", districtPloygon);
		return JsonUtils.objectToJson(map);
	}
	
	@RequestMapping("/get_place_list_by_name")
	public String getPlaceListByName(String type, String cityName,HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(type)){
			map.put("status", false);
			map.put("msg", "Type is null");
			return JsonUtils.objectToJson(map);
		}
		if(null == cityName){
			map.put("status", false);
			map.put("msg", "Gid is null");
			return JsonUtils.objectToJson(map);
		}
		QueryCollection collection = new QueryCollection();
		collection.setType(type);
		collection.setCityName(cityName);
		List<HashMap<String, Object>> districtPloygon = geoService.getDistrictListByName(collection);
		map.put("data", districtPloygon);
		return JsonUtils.objectToJson(map);
	}
	
	
	@RequestMapping("/oauth/downloadFile")
	public String downloadFile( ExportEntity exportEntity, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> dataIdList= new ArrayList<Integer>();
		boolean boo = false;
		String ftpHost = jedisClient.get("ftpIp");
		String ftpUserName = jedisClient.get("ftpName");
		String ftpPassword = jedisClient.get("ftpPwd");
		String proDownloadAdd=exportEntity.getPath();
		if(!StringUtils.isBlank(proDownloadAdd)){
			proDownloadAdd = "proDownloadAdd"+"/"+proDownloadAdd;
			// 获取到文件地址
			// reportPath = "/HY-1C/PMS/level0/ant.zip";
			String[] split = proDownloadAdd.split("/");
			String productName = split[split.length - 1];
			//proDownloadAdd=proDownloadAdd.substring(0, proDownloadAdd.lastIndexOf('/'));
			// String productName = "ant.zip";
			boo = FtpUtil.downloadFtpFile(request, response, proDownloadAdd, productName, ftpHost, ftpUserName,
					ftpPassword);
		}
		else{
			dataIdList.add(Integer.valueOf(exportEntity.getDataIds()));
			List<Metadata> metadataList = geoService.GetByDataId(dataIdList);
			if(metadataList!=null && !metadataList.isEmpty()){
				Metadata metadata=metadataList.get(0);
				// 获取到文件地址
				proDownloadAdd = metadata.getRepDownloadAdd();
				// reportPath = "/HY-1C/PMS/level0/ant.zip";
				String[] split = proDownloadAdd.split("/");
				String productName = split[split.length - 1];
				//proDownloadAdd=proDownloadAdd.substring(0, proDownloadAdd.lastIndexOf('/'));
				// String productName = "ant.zip";
				boo = FtpUtil.downloadFtpFile(request, response, proDownloadAdd, productName, ftpHost, ftpUserName,
						ftpPassword);
			}
		}
		
		map.put("status", boo);
		return JsonUtils.objectToJson(map);
	}
	
	/**
	 * 获取生产级别
	 * @author 乔森
	 * @param level
	 * @return
	 */
	public  String getProductionLevel(String level){
		String returnLevel = "";
		String levelConditions = jedisClient.get("levelCondition");
		String[] levelCondition = levelConditions.split(",");
		for (int i = 0; i < levelCondition.length; i++) {
			if(level.equals(levelCondition[i])){
				returnLevel = levelCondition[i-1];
			}
		}
//		SysConfig sysConfig = new SysConfig();
//		sysConfig.setConfigKey("levelCondition");
//		List<SysConfig> listConfig = sysConfigService.selectByKeyLevel(sysConfig);
//		listConfig.size();
//		for(int i=0;i<listConfig.size();i++){
//			SysConfig sysConfig1 = listConfig.get(i);
//			if(sysConfig1.getConfigValue().equals(level)&&i>0){
//				returnLevel = listConfig.get(i-1).getConfigValue();
//				continue;
//			}
//		}
		
		return returnLevel;
		
	}
	
	
	
	
	
	
	/** <pre>mapQuery(地图查询传感器单选)
	 * 创建人：josen 
	 * 创建时间：2018年4月10日 上午11:37:13    
	 */
	@RequestMapping("/oauth/updateDataStatusByMapQuery")
	public String updateDataStatusByMapQuery(@RequestBody QueryCollection queryModel, String isDelete){
//		public String updateDataStatusByMapQuery( QueryCollection queryModel, String isDelete){
		Map<String, Object> map = new HashMap<String, Object>();
		if(null==isDelete){
			map.put("status", false);
			map.put("msg", "isDelete is null");
			LOGGER.info("isDelete is null");
			return JsonUtils.objectToJson(map);
		}
		LOGGER.debug("用户信息："+queryModel.getToken());
		LOGGER.debug(queryModel.toString());
		User user = decryptToken.decyptToken(queryModel.getToken());
		if(null==user){
			map.put("status", false);
			map.put("msg", "user is null");
			LOGGER.info("user is null");
			return JsonUtils.objectToJson(map);
		}
//		String type = queryModel.getWorkType();
		String productLevels = queryModel.getProductLevel();
		String satellites = queryModel.getSatellite();
		String sensors = queryModel.getSensor();
//		给将卫星改为'卫星','卫星'格式方便查询
		if(!StringUtils.isBlank(satellites)){
			String sates = new String();
			for(String satellite:satellites.split(",")){
				sates+="'"+satellite+"',";
			}
			satellites = sates.substring(0, sates.length()-1);
			queryModel.setSatellite(satellites);
		}
		if(!StringUtils.isBlank(sensors)){
			String sens = new String();
			for(String sensor:sensors.split(",")){
				sens+="'"+sensor+"',";
			}
			sensors = sens.substring(0, sens.length()-1);
			queryModel.setSensor(sensors);
		}
		
		if(!StringUtils.isBlank(productLevels)){
			String levels = new String();
			for(String productLevel : productLevels.split(",")){
				levels+="'LEVE"+productLevel+"',";
			}
			productLevels = levels.substring(0, levels.length()-1);
			queryModel.setProductLevel(productLevels);
		}
		
		// 质量等级
		String qualityGrade = jedisClient.get("qualityGrade");
		queryModel.setQualityGrade(qualityGrade);
		
//		ObjAnalysis objAnalysis = new ObjAnalysis();
//		Map<String, Object> pmap = objAnalysis.ConvertObjToMap(queryModel);
//		pmap.put("isDelete", Integer.parseInt(isDelete));
		queryModel.setIsDelete(Integer.parseInt(isDelete));
		
		int i = geoService.updateDataStatusByMapQuery(queryModel);
		if(i>0){
			map.put("status", true);
			map.put("msg", "已成功修改"+i+"条数据");
		}else{
			map.put("status", true);
			map.put("msg", "没有满足条件的数据");
		}
		
		return JsonUtils.objectToJson(map);
	}
//	public static void main(String[] args) {
//					  //[{\"id\": \"236\",\"value\": \"HY-1C:AIS:L1B\"},{\"id\": \"947\",\"value\": \"HY-1C:AIS:L1A\"}]
//					 //"[{\"id\": \"236\",\"value\": \"HY-1C:AIS:L1B\"},{\"id\": \"947\",\"value\": \"HY-1C:AIS:L1A\"}]"
//		String atr = "[{\"id\": \"236\",\"value\": \"HY-1C:AIS:L1B\"},{\"id\": \"947\",\"value\": \"HY-1C:AIS:L1A\"}]";
//		JSONArray jsonArray = JSONArray.parseArray(atr);
//		System.out.println(jsonArray.get(0));
//	}
	
	
	
	
}
