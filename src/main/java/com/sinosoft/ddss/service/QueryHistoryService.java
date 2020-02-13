package com.sinosoft.ddss.service;

import java.math.BigDecimal;
import java.util.List;

import com.sinosoft.ddss.common.entity.QueryHistory;


/**
 * @author li_jiazhi
 * @create 2018年3月26日上午10:47:44
 * 历史记录查询接口
 */
public interface QueryHistoryService {
	
    /**
     * @param id
     * @return
     * @author li_jiazhi
     * @create 2018年3月26日下午4:33:34
     *   根据id删除
     */
    int deleteByPrimaryKey(Long id);

    int insert(QueryHistory record);

    /**
     * @param record
     * @return
     * @author li_jiazhi
     * @create 2018年3月26日下午4:33:48
     *   根据条件插入
     */
    int insertSelective(QueryHistory record);

    /**
     * @param id
     * @return
     * @author li_jiazhi
     * @create 2018年3月26日下午4:34:05
     *   根据id查询
     */
    QueryHistory selectByPrimaryKey(Long id);

    /**
     * @param record
     * @return
     * @author li_jiazhi
     * @create 2018年3月26日下午4:34:22
     *   根据id修改
     */
    int updateByPrimaryKeySelective(QueryHistory record);

    int updateByPrimaryKey(QueryHistory record);

	/**
	 * @param userName
	 * @return
	 * @author li_jiazhi
	 * @create 2018年3月26日下午5:17:38
	 *   根据用户名查询历史查询记录
	 */
	List<QueryHistory> selectByUserId(BigDecimal userId);

	/**
	 * id查询
	 * @param id
	 * @return
	 */
	QueryHistory selectQueryHistoryById(Long id);
}
