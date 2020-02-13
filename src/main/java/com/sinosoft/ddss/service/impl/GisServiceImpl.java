package com.sinosoft.ddss.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.ddss.common.constant.Constant;
import com.sinosoft.ddss.common.entity.Metadata;
import com.sinosoft.ddss.dataDao.MetadataQueryMapper;
import com.sinosoft.ddss.jedis.JedisClient;
import com.sinosoft.ddss.model.QueryCollection;
import com.sinosoft.ddss.service.GeoService;
import com.sinosoft.ddss.service.GisService;
import com.sinosoft.ddss.utils.ReturnSSPXmlUtils;
import com.sinosoft.ddss.utils.ReturnXMLUtil;
import com.sinosoft.ddss.utils.XmlToMapUtils;
@WebService(serviceName = "gis", // 与接口中指定的name一致
targetNamespace = "http://service.ddss.sinosoft.com/", // 与接口中的命名空间一致,一般是接口的包名倒
endpointInterface = "com.sinosoft.ddss.service.GisService"// 接口地址
)
@Service
@Transactional
public class GisServiceImpl implements GisService{
	@Autowired
	private MetadataQueryMapper metadataQueryMapper;
	@Autowired
	private GeoService geoService;
	@Autowired
	private JedisClient jedisClient;
	/* 
	 *  数据检索与反馈
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String SSP_DDSS_DATARETRIEVAL(String xmlInfo) {
		System.out.println(xmlInfo);
		// 获取Xml头部信息
		Map<String, Object> headMap = XmlToMapUtils.getHeadMap(xmlInfo);
		//接口实体类型
		String messageType = (String) headMap.get(Constant.MESSAGE_TYPE);
		//消息标识号，由接口服务调用方负责填写，递增到上限后可回滚
		String messageID = (String) headMap.get(Constant.MESSAGE_ID);
		//发送方
		String originatorAddress = (String) headMap.get(Constant.ORIGINATOR_ADDRESS);
		//接收方
		String recipientAddress = (String) headMap.get(Constant.RECIPIENT_ADDRESS);
		
		/*String result=ValidatorXML.validatorXml(xmlInfo, "SSP/SSP_DDSS_DATARETRIEVAL");
		
		if(result.contains(Constant.REPLYINFO_FAIL)){
			return ReturnXMLUtil.xmlResult(false, messageType, messageID, originatorAddress, recipientAddress, result);
		}*/
		
		Map<String, Object> paramMap = XmlToMapUtils.getParamMap(xmlInfo);
		//卫星列表
		Map<String, Object> sat = (Map<String, Object>) paramMap.get("SatList");
		List<Object> satList = new ArrayList<Object>();
		if(sat.get("satelliteID") instanceof ArrayList){
			satList = (List<Object>) sat.get("satelliteID");
		}
		else{
			satList.add(sat.get("satelliteID"));
		}
		String charSatList="";
		for (Object object : satList) {
			charSatList+="'"+(String) object+"',";
		}
		charSatList=charSatList.substring(0,charSatList.length()-1);
		//传感器列表
		Map<String, Object> sensor = (Map<String, Object>) paramMap.get("SensorList");
		List<Object> sensorList = new ArrayList<Object>();
		if(sensor.get("sensorID") instanceof ArrayList){
			sensorList = (List<Object>) sensor.get("sensorID");
		}
		else{
			sensorList.add(sensor.get("sensorID"));
		}
		String charSensorList="";
		for (Object object : sensorList) {
			charSensorList+="'"+(String) object+"',";
		}
		charSensorList=charSensorList.substring(0,charSensorList.length()-1);
		//卫星级别列表
		Map<String, Object> level = (Map<String, Object>) paramMap.get("LevelList");
		List<Object> levelList = new ArrayList<Object>();
		if(level.get("levelID") instanceof ArrayList){
			levelList = (List<Object>) level.get("levelID");
		}
		else{
			levelList.add(level.get("levelID"));
		}

		String charLevelList="";

		for (Object object : levelList) {
			charLevelList+="'"+(String) object+"',";
		}
		charLevelList=charLevelList.substring(0,charLevelList.length()-1);
		
		//查询起始日期
		String viewStartTime=(String)paramMap.get("viewStartTime");
		//查询结束日期
		String viewEndTime=(String)paramMap.get("viewEndTime");
		//空间范围
		String imageBoundary =(String)paramMap.get("imageBoundary");
		//当前页数
		String currentPage=(String)paramMap.get("currentPage");
		//每页记录数
		String pageSize=(String)paramMap.get("pageSize");
		QueryCollection queryModel=new QueryCollection();
		queryModel.setTimeType("image_start_time");
//		queryModel.setSatellite(charSatList);
//		queryModel.setSensor(charSensorList);
//		queryModel.setProductLevel(charLevelList);
		if(StringUtils.isNotBlank(viewStartTime)){
			queryModel.setStartTime(viewStartTime);
		}
		if(StringUtils.isNotBlank(viewEndTime)){
			queryModel.setEndTime(viewEndTime);
		}
		queryModel.setPloygon(imageBoundary);
//		queryModel.setPage(1);
		queryModel.setRegionalType("2");
		if(!StringUtils.isBlank(currentPage)){
			queryModel.setPage(Integer.parseInt(currentPage));
		}
		if(!StringUtils.isBlank(pageSize)){
			queryModel.setPageSize(Integer.parseInt(pageSize));
		}
		try {
//		queryBase.setMetadataQueryMapper(metadataQueryMapper);
		String imageIp = jedisClient.get("imageIp");
		String imagePort = jedisClient.get("imagePort");
		List<Metadata> metadataList = geoService.mapQuery(queryModel);
		for(Metadata metadata:metadataList){
			if(metadata.getThumbFileUrl()!=null){
				metadata.setThumbFileUrl(imageIp+":"+imagePort+"/"+metadata.getThumbFileUrl());
			}
		}
		Integer allcount = geoService.mapQueryCount(queryModel);
		Integer pageTotal = (allcount / queryModel.getPageSize()) + ((allcount % queryModel.getPageSize()) > 0 ? 1 : 0);
			return ReturnSSPXmlUtils.SSP_DDSS_DATARETRIEVAL(messageType, messageID, originatorAddress, recipientAddress, metadataList, allcount, pageTotal);
		} catch (Exception e) {
			return ReturnXMLUtil.xmlResult(false, messageType, messageID, originatorAddress, recipientAddress, e.getMessage());
		}
	}
}
