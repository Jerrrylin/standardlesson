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

@Component//ע��bean
public class MessageService {
	@Resource//ʹ��������bean
	private  SlMessageDao slMessageDao;
	
	@Resource
	private SlEmployeeDao slEmployeeDao;
	
	@DataResolver
	@Transactional//����
	public void saveMessages(Collection<SlEmployee> employees){
		//����ÿ���˵�ÿ���ʼ�
		for( SlEmployee employee:employees){
			Collection<SlMessage> messages = employee.getSlMessageSet();
			for(SlMessage message:messages){
				//��ÿ���ʼ�����employee   ά������ʼ���Ӧһ���˵Ĺ�ϵ
				message.setSlEmployee(employee);
			}
			//����ÿ�����������е��ʼ�
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
	@Transactional//�������
	public void saveAll(Collection<SlEmployee> employees){
		//ά������Ĺ�ϵ�����Ǳ���employee����Ϣ��
//		System.out.println("employees="+employees);
		slEmployeeDao.persistEntities(employees);//�ӳ��Ա���û���ύ����Ļ����ᱣ��
		for(SlEmployee employee: employees){
			Set<SlMessage> messages = employee.getSlMessageSet();
			//�ҵ�ÿ��employee��Ϣ�µ�message���ж��Ƿ�Ϊnull
			if(null!=messages){//����������м��ϵ�ʱ��
//				System.out.println("messages="+messages);
				for(SlMessage message:messages){
					//��Ϊ�յĻ������ʼ���  ά���ӱ�
					message.setSlEmployee(employee);
				}
				//��������������е��ʼ�
				slMessageDao.persistEntities(messages);//�ӳ��Ա���û���ύ����Ļ����ᱣ��
			}
		}
	}
}
