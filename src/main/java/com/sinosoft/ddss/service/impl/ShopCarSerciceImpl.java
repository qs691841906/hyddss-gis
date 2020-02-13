package com.sinosoft.ddss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.ddss.common.entity.ShopInfo;
import com.sinosoft.ddss.common.entity.query.ShopInfoQuery;
import com.sinosoft.ddss.common.util.DateTimeUtils;
import com.sinosoft.ddss.dao.ShopInfoMapper;
import com.sinosoft.ddss.service.ShopCarService;

@Service
public class ShopCarSerciceImpl implements ShopCarService {

	@Autowired
	private ShopInfoMapper shopInfoMapper;

	/**
	 * 查询购物车列表
	 */
	@Override
	public List<ShopInfo> ListShopInfo(ShopInfoQuery record) {
		return shopInfoMapper.listShopInfo(record);
	}

	/**
	 * 新增购物车数据
	 */
	@Override
	public int insertSelective(ShopInfo record) {
		record.setCreateTime(DateTimeUtils.getNowStrTime());
		return shopInfoMapper.insertSelective(record);
	}

	/**
	 * 查询购物车数量
	 */
	@Override
	public Integer getCountByQuery(ShopInfoQuery record) {
		return shopInfoMapper.getCountByQuery(record);
	}

	/**
	 * in删除
	 */
	@Override
	public int deleteByPrimaryKey(String ids) {
		return shopInfoMapper.deleteByPrimaryKey(ids);
	}

	/**
	 * 清空购物车
	 */
	@Override
	public int delAllShopCar(String userName) {
		return shopInfoMapper.delAllShopCar(userName);
	}

	/**
	 * 根据id查找购物车信息
	 */
	@Override
	public List<ShopInfo> selectByShopCarIds(String dataIds) {
		List<ShopInfo> listShopCar = shopInfoMapper.selectByPrimaryKeys(dataIds);
		return listShopCar;
	}

	/**
	 * 根据id查找购物车信息
	 */
	@Override
	public List<ShopInfo> selectByDataIds(String dataIds) {
		List<ShopInfo> listShopCar = shopInfoMapper.selectByDataIds(dataIds);
		return listShopCar;
	}

}
