package com.bstek.dorado.sample.standardlesson.service;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import antlr.collections.List;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.sample.standardlesson.dao.SICompanyDao;
import com.bstek.dorado.sample.standardlesson.entity.SlCompany;
@Component//����ͨ����ʵ������dao��
public class CompanyService {
	@Resource//����ע��
	private SICompanyDao siCompanyDao;
	
//����һ�������ص���collection��		
	@DataProvider//���Զ�DataSet�ṩ���ݼ��ط���
	public Collection<SlCompany>getCompany(){
		return siCompanyDao.getAll();
	}
	
	//������,���ص���һ������
/*	@DataProvider
	public SlCompany getCompany(){
		java.util.List<SlCompany>list=siCompanyDao.getAll();
		for(SlCompany company:list){
			return company;
		}
		return null;
	}*/
	@DataResolver
	@Transactional//�����ʾ
	public void saveCompany(Collection<SlCompany>slCompanies){
		siCompanyDao.persistEntities(slCompanies);
	}

}
