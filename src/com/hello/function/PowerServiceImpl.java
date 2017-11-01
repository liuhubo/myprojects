package com.hello.function;

import java.lang.reflect.AccessibleObject;

public class PowerServiceImpl implements PowerService{

	@Override
	public String getPowerBikeElectr(Long eletri) {
		return new StringBuilder(eletri.toString()).append("%").toString();
	}

}
