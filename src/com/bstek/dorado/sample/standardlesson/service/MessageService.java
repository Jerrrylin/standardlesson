package com.bstek.dorado.sample.standardlesson.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.map.Map;
import com.bstek.dorado.sample.standardlesson.dao.SlEmployeeDao;
import com.bstek.dorado.sample.standardlesson.dao.SlMessageDao;

import com.bstek.dorado.sample.standardlesson.entity.SlEmployee;
import com.bstek.dorado.sample.standardlesson.entity.SlMessage;

@Component//注册bean
public class MessageService {
	@Resource//使用其他的bean
	private  SlMessageDao slMessageDao;
	
	@Resource
	private SlEmployeeDao slEmployeeDao;
	
	@DataResolver
	@Transactional//事务
	public void saveMessages(Collection<SlEmployee> employees){
		//遍历每个人的每个邮件
		for( SlEmployee employee:employees){
			Collection<SlMessage> messages = employee.getSlMessageSet();
			for(SlMessage message:messages){
				//给每个邮件设置employee   维护多封邮件对应一个人的关系
				message.setSlEmployee(employee);
			}
			//保存每个人下面所有的邮件
				slMessageDao.persistEntities(messages);
		}
	}
	
	@DataProvider
	public void  getMessageByEmployeeId(Page<SlMessage> page,Integer employeeId){
		String hql = "from SlMessage where slEmployee.employeeId = :employeeId";
		java.util.Map param =new HashMap();
		param.put("employeeId", employeeId);
		slMessageDao.find(page, hql, param);
	}
	@DataResolver
	@Transactional//事务管理
	public void saveAll(Collection<SlEmployee> employees){
		//维护主表的关系（就是保存employee的信息）
//		System.out.println("employees="+employees);
		slEmployeeDao.persistEntities(employees);//延迟性保存没有提交事务的话不会保存
		for(SlEmployee employee: employees){
			Set<SlMessage> messages = employee.getSlMessageSet();
			//找到每个employee信息下的message，判断是否为null
			if(null!=messages){//这个人下面有集合的时候
//				System.out.println("messages="+messages);
				for(SlMessage message:messages){
					//不为空的话设置邮件人  维护从表
					message.setSlEmployee(employee);
				}
				//保存这个人下所有的邮件
				slMessageDao.persistEntities(messages);//延迟性保存没有提交事务的话不会保存
			}
		}
	}
}
