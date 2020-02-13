package com.sinosoft.ddss.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author yjd
 * @create 2018年3月15日下午4:28:45 暴露的webservice接口
 */
@WebService(name = "gis", // 暴露服务名称
		targetNamespace = "http://service.ddss.sinosoft.com/"// 命名空间,一般是接口的包名倒序
)
public interface GisService {
	/**
	 * 数据检索与反馈
	 * 
	 * @param xmlInfo
	 * @return
	 * @author yjd
	 * @create 2018年4月18日下午4:28:34 用户观测需求
	 */
	@WebMethod
	public String SSP_DDSS_DATARETRIEVAL(String xmlInfo);
}
