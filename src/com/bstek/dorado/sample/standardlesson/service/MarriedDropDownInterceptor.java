package com.bstek.dorado.sample.standardlesson.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;

@Component
public class MarriedDropDownInterceptor {
	@DataProvider
	public Map<String,String> getMarriedState(){
		Map<String,String> mapValue = new LinkedHashMap<String, String>();
		mapValue.put("true", "ÒÑ»é");
		mapValue.put("false", "Î´»é");
		return mapValue;
	}
}