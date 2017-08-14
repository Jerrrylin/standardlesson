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
@Component//把普通容器实例化到dao中
public class CompanyService {
	@Resource//依赖注入
	private SICompanyDao siCompanyDao;
	
//方法一，当返回的是collection的		
	@DataProvider//用以对DataSet提供数据加载服务
	public Collection<SlCompany>getCompany(){
		return siCompanyDao.getAll();
	}
	
	//方法二,返回的是一个对象
/*	@DataProvider
	public SlCompany getCompany(){
		java.util.List<SlCompany>list=siCompanyDao.getAll();
		for(SlCompany company:list){
			return company;
		}
		return null;
	}*/
	@DataResolver
	@Transactional//事务标示
	public void saveCompany(Collection<SlCompany>slCompanies){
		siCompanyDao.persistEntities(slCompanies);
	}

}
