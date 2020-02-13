
package com.sinosoft.ddss.service;

import java.util.List;
import java.util.Map;

import com.sinosoft.ddss.common.entity.SysConfig;
import com.sinosoft.ddss.common.entity.query.SysConfigQuery;

public interface ISysConfigService {

	List<SysConfig> selectByKeyLevel(SysConfig sysConfig);

	
}
