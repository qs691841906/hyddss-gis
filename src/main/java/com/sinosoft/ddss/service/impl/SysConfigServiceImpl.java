
package com.sinosoft.ddss.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.ddss.common.entity.SysConfig;
import com.sinosoft.ddss.dao.SysConfigMapper;
import com.sinosoft.ddss.service.ISysConfigService;

@Service
@Transactional
public class SysConfigServiceImpl implements ISysConfigService {
	private static Logger LOGGER = LoggerFactory.getLogger(SysConfigServiceImpl.class);
	@Autowired
	private SysConfigMapper sysConfigMapper;

	@Override
	public List<SysConfig> selectByKeyLevel(SysConfig sysConfig) {
		return sysConfigMapper.selectByKeyLevel(sysConfig);
	}

	
	
	
	
	
}
