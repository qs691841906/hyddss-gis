package com.sinosoft.ddss.dao;

import com.sinosoft.ddss.common.entity.SysConfig;
import com.sinosoft.ddss.common.entity.query.SysConfigQuery;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

public interface SysConfigMapper {
	
	List<SysConfig> selectByKeyLevel(SysConfig sysConfig);

}