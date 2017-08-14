package com.bstek.dorado.sample.standardlesson.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.sound.midi.MidiDevice.Info;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.core.DoradoAbout;
@Component//通用注解
public class SystemInfoService {
	
	@Expose//把这个服务暴露出来
	public Properties getSystemInfo(){
		Properties info = new Properties();//用于读取Java的配置文件
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  hh:mm:ss");
		info.setProperty("product", DoradoAbout.getProductTitle());
		info.setProperty("vendor", DoradoAbout.getVendor());
		info.setProperty("version", DoradoAbout.getVersion());
		info.setProperty("time", sdf.format(new Date()));
		return info;
	}
}
