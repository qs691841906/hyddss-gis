package com.sinosoft.ddss.service;

import com.sinosoft.ddss.common.entity.Additional;

public interface OrderMainService {

	/**
	 * 根据主订单id获取主单信息
	 * @param orderMainId
	 * @return
	 */
	Additional getOrderMainById(Long orderMainId);

}
