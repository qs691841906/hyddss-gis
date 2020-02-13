package com.sinosoft.ddss.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class TestApp {

	@Test
	public void ttt(){
		String satellateRes = " AND (";
		String satellites = "HY-2B:RA,RAD#HY-2C:SCA,RC";
		if(!StringUtils.isBlank(satellites)){
			String[] satesens = satellites.split("#");
			for (String satesen : satesens) {
				String[] sate = satesen.split(":");
				String satellite = sate[0];
				String sensors = sate[1];
				String[] sensorss = sensors.split(",");
				for (String sensor : sensorss) {
					satellateRes += " (dm.satellite = '" + satellite + "' AND dm.sensor = '" + sensor + "') OR";
				}
			}
		}
		satellateRes = satellateRes.substring(0, satellateRes.length()-3);
		satellateRes += ")";
		System.out.println(satellateRes);
	}
}
