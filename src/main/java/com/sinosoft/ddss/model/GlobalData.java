package com.sinosoft.ddss.model;

import java.math.BigDecimal;

public class GlobalData {

	private BigDecimal queryArea;

	private BigDecimal unionArea;

	private BigDecimal intersectionArea;

	private String geomStr;

	private Integer count;
	
	private String orderGeom;

	public String getOrderGeom() {
		return orderGeom;
	}

	public void setOrderGeom(String orderGeom) {
		this.orderGeom = orderGeom;
	}

	public BigDecimal getQueryArea() {
		return queryArea;
	}

	public void setQueryArea(BigDecimal queryArea) {
		this.queryArea = queryArea;
	}

	public BigDecimal getUnionArea() {
		return unionArea;
	}

	public void setUnionArea(BigDecimal unionArea) {
		this.unionArea = unionArea;
	}

	public BigDecimal getIntersectionArea() {
		return intersectionArea;
	}

	public void setIntersectionArea(BigDecimal intersectionArea) {
		this.intersectionArea = intersectionArea;
	}

	public String getGeomStr() {
		return geomStr;
	}

	public void setGeomStr(String geomStr) {
		this.geomStr = geomStr;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
