package com.sinosoft.ddss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.ddss.common.entity.Additional;
import com.sinosoft.ddss.dao.OrderInfoMainMapper;
import com.sinosoft.ddss.service.OrderMainService;

@Service
public class OrderMainServiceImpl implements OrderMainService {

	@Autowired
	private OrderInfoMainMapper orderMainMapper;

	/**
	 * 根据主订单id获取主单信息
	 */
	@Override
	public Additional getOrderMainById(Long orderMainId) {
		return orderMainMapper.getOrderMainById(orderMainId);
	}

}
