package com.sinosoft.ddss.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.ddss.common.entity.QueryHistory;
import com.sinosoft.ddss.common.entity.User;
import com.sinosoft.ddss.dao.QueryHistoryMapper;
import com.sinosoft.ddss.service.DecryptToken;
import com.sinosoft.ddss.service.QueryHistoryService;


/**
 * @author li_jiazhi
 * @create 2018年3月26日上午10:47:19
 * 历史记录查询接口实现
 */
@Service
@Transactional
public class QueryHistoryServiceImpl implements QueryHistoryService {

	@Autowired
	private QueryHistoryMapper queryHistoryMapper;

	@Autowired
	private DecryptToken decryptToken;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return queryHistoryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(QueryHistory record) {
		return queryHistoryMapper.insert(record);
	}

	@Override
	public int insertSelective(QueryHistory record) {
		String token = record.getToken();
		User user = decryptToken.decyptToken(token);
		if(user==null){
			return 0;
		}
		record.setUserId(user.getUserId());
		return queryHistoryMapper.insertSelective(record);
	}

	@Override
	public QueryHistory selectByPrimaryKey(Long id) {
		return queryHistoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(QueryHistory record) {
		return queryHistoryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(QueryHistory record) {
		return queryHistoryMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<QueryHistory> selectByUserId(BigDecimal userId) {
		return queryHistoryMapper.selectByUserId(userId);
	}

	@Override
	public QueryHistory selectQueryHistoryById(Long id) {
		return queryHistoryMapper.selectByPrimaryKey(id);
	}


}
